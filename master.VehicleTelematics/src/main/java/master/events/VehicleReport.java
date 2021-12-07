package master.events;

/**
 * @author Wenqi Jiang & Zhou
 */
public class VehicleReport {
    /**
     * Being Time a timestamp (integer) in seconds identifying the time at
     * which the position event was emitted,
     */
    private Long timestamp;
    /**
     * VID is an integer that identifies the vehicle,
     */
    private int vehicleId;
    /**
     * Spd: (0 - 100) is an integer that represents the speed in mph (miles per hour),
     */
    private int speed;
    /**
     * XWay: (0 . . .L−1) identifies the highway from which the position report is emitted
     */
    private int xWay;
    /**
     *  Lane: (0 . . . 4) identifies the lane of the highway from which the position report is emitted
     *  0 if it is an entrance ramp (ENTRY),
     *  1 − 3 if it is a travel lane (TRAVEL)
     *  and 4 if it is an exit ramp (EXIT)).
     */
    private int lane;
    /**
     * Dir: (0 . . . 1) indicates the direction (0 for Eastbound and 1 for Westbound) the vehicle is traveling.
     */
    private int direction;
    /**
     * Seg: (0 . . . 99) identifies the segment from which the position report is emitted
     */
    private int segment;

    /**
     * Pos (0 . . . 527999) identifies the horizontal position of the vehicle as the number of meters
     * from the westernmost point on the highway (i.e., Pos = x)
     */
    private int position;

    public VehicleReport(Long timestamp, int vehicleId, int speed, int xWay, int lane, int direction, int segment, int position) {
        this.timestamp = timestamp;
        this.vehicleId = vehicleId;
        this.speed = speed;
        this.xWay = xWay;
        this.lane = lane;
        this.direction = direction;
        this.segment = segment;
        this.position = position;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public int getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(int vehicleId) {
        this.vehicleId = vehicleId;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getxWay() {
        return xWay;
    }

    public void setxWay(int xWay) {
        this.xWay = xWay;
    }

    public int getLane() {
        return lane;
    }

    public void setLane(int lane) {
        this.lane = lane;
    }

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public int getSegment() {
        return segment;
    }

    public void setSegment(int segment) {
        this.segment = segment;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
