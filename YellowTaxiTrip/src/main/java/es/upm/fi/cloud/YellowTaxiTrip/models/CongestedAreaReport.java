package es.upm.fi.cloud.YellowTaxiTrip.models;

import es.upm.fi.cloud.YellowTaxiTrip.utils.OutputFormatter;
import java.util.Date;

/**
 * @author wenqi
 */
public class CongestedAreaReport {
    private Date date;
    private int numberOfTrips;
    private double costAvg;


    public CongestedAreaReport() {
    }

    public CongestedAreaReport(Date date, int numberOfTrips, double costAvg) {
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

    @Override
    public String toString() {
        return OutputFormatter.DATE_FORMAT.format(date) + "," + numberOfTrips + "," + OutputFormatter.DECIMAL_FORMAT.format(costAvg);

    }


}
