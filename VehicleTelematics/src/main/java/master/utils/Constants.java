package master.utils;

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

}
