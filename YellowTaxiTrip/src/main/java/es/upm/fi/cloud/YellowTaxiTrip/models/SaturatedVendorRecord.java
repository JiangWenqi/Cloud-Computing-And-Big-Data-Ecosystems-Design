package es.upm.fi.cloud.YellowTaxiTrip.models;

import java.util.Date;

/**
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
        return "SaturatedVendorRecord{" +
                "vendorId=" + vendorId +
                ", startTime=" + startTime +
                ", finishTime=" + finishTime +
                ", numberOfTrips=" + numberOfTrips +
                '}';
    }
}
