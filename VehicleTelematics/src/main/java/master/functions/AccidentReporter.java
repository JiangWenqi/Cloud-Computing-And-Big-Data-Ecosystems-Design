package master.functions;

import master.events.AccidentReport;
import master.events.VehicleReport;
import master.utils.AccidentKey;
import master.utils.Constants;
import org.apache.flink.api.common.functions.FilterFunction;
import org.apache.flink.api.java.functions.KeySelector;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.functions.windowing.WindowFunction;
import org.apache.flink.streaming.api.windowing.windows.GlobalWindow;
import org.apache.flink.util.Collector;

import java.util.Iterator;

/** @author Vinci */
public class AccidentReporter {

  public static DataStream<AccidentReport> report(DataStream<VehicleReport> vehicleReports) {

    return vehicleReports
        .assignTimestampsAndWatermarks(Constants.WATERMARK_STRATEGY)
        .filter((FilterFunction<VehicleReport>) report -> report.speed == 0)
        .keyBy(new AccidentKeySelector())
        .countWindow(Constants.ACCIDENT_REPORT_COUNT, 1)
        .apply(new AccidentReportFunction());
  }
}

class AccidentKeySelector implements KeySelector<VehicleReport, AccidentKey> {

  @Override
  public AccidentKey getKey(VehicleReport report) throws Exception {
    return new AccidentKey(
        report.vehicleId, report.xWay, report.direction, report.segment, report.position);
  }
}

class AccidentReportFunction
    implements WindowFunction<VehicleReport, AccidentReport, AccidentKey, GlobalWindow> {

  @Override
  public void apply(
      AccidentKey accidentKey,
      GlobalWindow window,
      Iterable<VehicleReport> vehicleReports,
      Collector<AccidentReport> accidentReports)
      throws Exception {

    int counter = 1;

    Iterator<VehicleReport> iterator = vehicleReports.iterator();

    long time1 = iterator.next().timestamp;
    while (iterator.hasNext()) {
      counter++;
      VehicleReport vehicleReport = iterator.next();
      // We will report an accident if vehicle reports 4 consecutive events from the same position
      if (counter == Constants.ACCIDENT_REPORT_COUNT) {
        AccidentReport accidentReport =
            new AccidentReport(
                time1,
                vehicleReport.timestamp,
                accidentKey.getVId(),
                accidentKey.getXWay(),
                accidentKey.getSeg(),
                accidentKey.getDir(),
                accidentKey.getPos());
        accidentReports.collect(accidentReport);
      }
    }
  }
}
