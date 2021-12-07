package master.functions;

import master.events.SpeedFine;
import master.events.VehicleReport;
import master.utils.ConfigUtil;
import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.common.functions.RichFlatMapFunction;
import org.apache.flink.util.Collector;

import java.util.Properties;

/**
 * Issue a ticket (Time, VID, XWay, Seg, Dir, Spd)
 *
 * @author Wenqi Jiang & Zhou
 */
public class SpeedRadar implements FlatMapFunction<VehicleReport, SpeedFine> {
    private static final int MAX_SPEED = Integer.parseInt(new ConfigUtil().getProperty("max.speed"));

    @Override
    public void flatMap(VehicleReport report, Collector<SpeedFine> speedFines) throws Exception {
        if (report.speed > MAX_SPEED) {
            speedFines.collect(
                    new SpeedFine(report.timestamp, report.vehicleId, report.xWay, report.segment, report.direction, report.speed)
            );
        }
    }
}
