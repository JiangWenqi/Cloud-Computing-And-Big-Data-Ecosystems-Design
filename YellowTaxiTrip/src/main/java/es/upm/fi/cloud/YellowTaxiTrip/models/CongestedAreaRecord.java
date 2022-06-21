package es.upm.fi.cloud.YellowTaxiTrip.models;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author wenqi
 */
public class CongestedAreaRecord {
    private Date date;
    private int numberOfTrips;
    private double costAvg;

    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private final DecimalFormat decimalFormat = new DecimalFormat("##.00");

    private CongestedAreaRecord() {
        date = null;
        numberOfTrips = 0;
        costAvg = 0;
    }

    public CongestedAreaRecord(Date date, int numberOfTrips, double costAvg) {
        this.date = date;
        this.numberOfTrips = numberOfTrips;
        this.costAvg = costAvg;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getNumberOfTrips() {
        return numberOfTrips;
    }

    public void setNumberOfTrips(int numberOfTrips) {
        this.numberOfTrips = numberOfTrips;
    }

    public double getCostAvg() {
        return costAvg;
    }

    public void setCostAvg(double costAvg) {
        this.costAvg = costAvg;
    }

    /**
     * Outputting the report as csv format
     * Example: 2022/03/01,102996,18.98
     */
    @Override
    public String toString() {

        return dateFormat.format(date) + "," + numberOfTrips + "," + decimalFormat.format(costAvg);
    }


}
