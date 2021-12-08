package master.events;

/**
 * Vehicle Report POJO
 *
 * @author Wenqi Jiang & Zhou
 */
public class VehicleReport {
    // Ensure the order after pojo serialization

    /**
     * Being Time a timestamp (integer) in seconds identifying the time at
     * which the position event was emitted,
     */
    public Long timestamp;
    /**
     * VID is an integer that identifies the vehicle,
     */
    public int vehicleId;
    /**
     * Spd: (0 - 100) is an integer that represents the speed in mph (miles per hour),
     */
    public int speed;
    /**
     * XWay: (0 . . .L−1) identifies the highway from which the position report is emitted
     */
    public int xWay;
    /**
     *  Lane: (0 . . . 4) identifies the lane of the highway from which the position report is emitted
     *  0 if it is an entrance ramp (ENTRY),
     *  1 − 3 if it is a travel lane (TRAVEL)
     *  and 4 if it is an exit ramp (EXIT)).
     */
    public int lane;
    /**
     * Dir: (0 . . . 1) indicates the direction (0 for Eastbound and 1 for Westbound) the vehicle is traveling.
     */
    public int direction;
    /**
     * Seg: (0 . . . 99) identifies the segment from which the position report is emitted
     */
    public int segment;

    /**
     * Pos (0 . . . 527999) identifies the horizontal position of the vehicle as the number of meters
     * from the westernmost point on the highway (i.e., Pos = x)
     */
    public int position;





    @Override
    public String toString() {
        return "VehicleReport{" +
                "timestamp=" + timestamp +
                ", vehicleId=" + vehicleId +
                ", speed=" + speed +
                ", xWay=" + xWay +
                ", lane=" + lane +
                ", direction=" + direction +
                ", segment=" + segment +
                ", position=" + position +
                '}';
    }
}

