package master.functions;

import master.events.AvgSpeedFine;
import master.events.VehicleReport;
import master.utils.AverageSpeedKey;
import master.utils.ConfigUtil;
import master.utils.Constants;
import org.apache.flink.api.common.functions.FilterFunction;
import org.apache.flink.api.java.functions.KeySelector;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.functions.windowing.WindowFunction;
import org.apache.flink.streaming.api.windowing.assigners.GlobalWindows;
import org.apache.flink.streaming.api.windowing.windows.GlobalWindow;
import org.apache.flink.util.Collector;

/** @author Wenqi Jiang & Zhou */
public class AverageSpeedController {

  public static DataStream<AvgSpeedFine> issueFines(DataStream<VehicleReport> vehicleReports) {
    DataStream<VehicleReport> rangeReports =
        vehicleReports.filter(
            (FilterFunction<VehicleReport>) report -> report.segment >= 52 && report.segment <= 56);


    return rangeReports
        .keyBy(new AverageSpeedKeySelector())
        .window(GlobalWindows.create())
        .apply(new AverageSpeedCollector());
  }
}
/** select average speed key(vID, xWay, dir) from vehicle reports */
class AverageSpeedKeySelector implements KeySelector<VehicleReport, AverageSpeedKey> {
  @Override
  public AverageSpeedKey getKey(VehicleReport report) throws Exception {
    return new AverageSpeedKey(report.vehicleId, report.xWay, report.direction);
  }
}

/** calculate average speed in range, if average speed above 60, then issue one tickets */
class AverageSpeedCollector
    implements WindowFunction<VehicleReport, AvgSpeedFine, AverageSpeedKey, GlobalWindow> {


  @Override
  public void apply(
      AverageSpeedKey averageSpeedKey,
      GlobalWindow window,
      Iterable<VehicleReport> vehicleReports,
      Collector<AvgSpeedFine> avgSpeedFines)
      throws Exception {
    long time1 = Integer.MAX_VALUE;
    int startPosition = Integer.MAX_VALUE;

    long time2 = 0;
    int endPosition = 0;

    for (VehicleReport vr : vehicleReports) {
      long currentTime = vr.timestamp;
      int currentPos = vr.position;
      time1 = Math.min(time1, currentTime);
      time2 = Math.max(time2, currentTime);
      startPosition = Math.min(startPosition, currentPos);
      endPosition = Math.max(endPosition, currentPos);
    }
    // Have to check that the car completes all segments 52->56

    if (time2 - time1 != 0) {
      // in mph
      double avgSpeed = (endPosition - startPosition) * Constants.Mile / (time2 - time1);
      if (avgSpeed > Constants.AVERAGE_MAX_SPEED) {
        AvgSpeedFine avgEvent =
            new AvgSpeedFine(
                time1,
                time2,
                averageSpeedKey.getVId(),
                averageSpeedKey.getXWay(),
                averageSpeedKey.getDir(),
                avgSpeed);
        avgSpeedFines.collect(avgEvent);
      }
    }
  }
}
