package master.functions;

import master.events.AvgSpeedFine;
import master.events.VehicleReport;
import master.utils.AverageSpeedKey;
import master.utils.Constants;
import org.apache.flink.api.common.functions.FilterFunction;
import org.apache.flink.api.java.functions.KeySelector;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.functions.windowing.WindowFunction;
import org.apache.flink.streaming.api.windowing.assigners.TumblingEventTimeWindows;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.streaming.api.windowing.windows.TimeWindow;
import org.apache.flink.util.Collector;

/** @author Wenqi Jiang & Zhuo Cheng  */
public class AverageSpeedController {

  public static DataStream<AvgSpeedFine> issueFines(DataStream<VehicleReport> vehicleReports) {
    return vehicleReports
        .assignTimestampsAndWatermarks(Constants.WATERMARK_STRATEGY)
        .filter(
            (FilterFunction<VehicleReport>) report -> report.segment >= 52 && report.segment <= 56)
        .keyBy(new AverageSpeedKeySelector())
        .window(TumblingEventTimeWindows.of(Time.minutes(5)))
        .apply(new AverageSpeedDetection());
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
class AverageSpeedDetection
    implements WindowFunction<VehicleReport, AvgSpeedFine, AverageSpeedKey, TimeWindow> {

  @Override
  public void apply(
      AverageSpeedKey averageSpeedKey,
      TimeWindow window,
      Iterable<VehicleReport> reports,
      Collector<AvgSpeedFine> avgSpeedFines)
      throws Exception {
    long time1 = Long.MAX_VALUE;
    int startPosition = Integer.MAX_VALUE;

    long time2 = Long.MIN_VALUE;
    int endPosition = Integer.MIN_VALUE;

    for (VehicleReport report : reports) {
      long currentTime = report.timestamp;
      int currentPos = report.position;
      time1 = Math.min(time1, currentTime);
      time2 = Math.max(time2, currentTime);
      startPosition = Math.min(startPosition, currentPos);
      endPosition = Math.max(endPosition, currentPos);
    }

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
