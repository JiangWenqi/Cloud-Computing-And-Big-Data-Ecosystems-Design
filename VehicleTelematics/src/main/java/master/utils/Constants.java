package master.utils;

import master.events.VehicleReport;
import org.apache.flink.api.common.eventtime.WatermarkStrategy;

import java.time.Duration;

/**
 * @author Wenqi Jiang
 */
public class Constants {
    public static final String[] VEHICLE_REPORT_FIELDS =
            new String[] {
                    "timestamp", "vehicleId", "speed", "xWay", "lane", "direction", "segment", "position"
            };
    public  static final double Mile = 2.23694;
    public  static final int MAX_SPEED = 90;
    public  static final int AVERAGE_MAX_SPEED = 60;
    public  static final int ACCIDENT_REPORT_COUNT = 4;

    public  static final WatermarkStrategy<VehicleReport> WATERMARK_STRATEGY = WatermarkStrategy.<VehicleReport>forBoundedOutOfOrderness(Duration.ofMinutes(1))
            .withTimestampAssigner((event, timestamp) -> event.timestamp * 1000);
}
