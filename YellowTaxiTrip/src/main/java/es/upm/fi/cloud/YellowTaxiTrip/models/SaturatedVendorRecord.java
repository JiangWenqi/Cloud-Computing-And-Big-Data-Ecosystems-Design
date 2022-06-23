package es.upm.fi.cloud.YellowTaxiTrip.models;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * vendorID, start of the first trip (tpep_pickup_datetime), finish time of the last trip, total number of consecutive trips.
 *
 * @author wenqi
 */
public class SaturatedVendorRecord {


    private Integer vendorId;
    private Date startTime;
    private Date finishTime;
    private Integer numberOfTrips;


    public SaturatedVendorRecord() {
        vendorId = null;
        startTime = null;
        finishTime = null;
        numberOfTrips = null;
    }

    public SaturatedVendorRecord(Integer vendorId, Date startTime, Date finishTime, Integer numberOfTrips) {

        this.vendorId = vendorId;
        this.startTime = startTime;
        this.finishTime = finishTime;
        this.numberOfTrips = numberOfTrips;
    }

    public Integer getVendorId() {
        return vendorId;
    }

    public void setVendorId(Integer vendorId) {
        this.vendorId = vendorId;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(Date finishTime) {
        this.finishTime = finishTime;
    }

    public Integer getNumberOfTrips() {
        return numberOfTrips;
    }

    public void setNumberOfTrips(Integer numberOfTrips) {
        this.numberOfTrips = numberOfTrips;
    }

    @Override
    public String toString() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return vendorId + "," + formatter.format(startTime) + "," + formatter.format(finishTime) + "," + numberOfTrips;
    }
}
