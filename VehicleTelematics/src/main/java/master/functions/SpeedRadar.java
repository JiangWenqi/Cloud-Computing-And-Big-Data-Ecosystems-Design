package master.functions;

import master.events.SpeedFine;
import master.events.VehicleReport;
import master.utils.ConfigUtil;
import master.utils.Constants;
import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.util.Collector;


/**
 * Issue a ticket (Time, VID, XWay, Seg, Dir, Spd)
 *
 * @author Wenqi Jiang & Zhou
 */
public class SpeedRadar {

  public static DataStream<SpeedFine> issueFines(DataStream<VehicleReport> vehicleReports) {
    return vehicleReports.flatMap(new SpeedRadarFlatMap());
  }
}

class SpeedRadarFlatMap implements FlatMapFunction<VehicleReport, SpeedFine> {

  @Override
  public void flatMap(VehicleReport report, Collector<SpeedFine> speedFines) {
    if (report.speed > Constants.MAX_SPEED) {
      speedFines.collect(
          new SpeedFine(
              report.timestamp,
              report.vehicleId,
              report.xWay,
              report.segment,
              report.direction,
              report.speed));
    }
  }
}
