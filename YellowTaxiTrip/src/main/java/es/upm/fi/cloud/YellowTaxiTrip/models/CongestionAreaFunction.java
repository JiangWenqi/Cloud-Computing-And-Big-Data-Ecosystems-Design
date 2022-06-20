package es.upm.fi.cloud.YellowTaxiTrip.models;

import org.apache.flink.streaming.api.functions.windowing.AllWindowFunction;
import org.apache.flink.streaming.api.windowing.windows.TimeWindow;
import org.apache.flink.util.Collector;

import java.util.Date;

/**
 * @author wenqi
 */
public class CongestionAreaFunction implements AllWindowFunction<TaxiReport, CongestedAreaReport, TimeWindow> {
    /**
     * Evaluates the window and outputs none or several elements.
     *
     * @param window              The window that is being evaluated.
     * @param taxiReports         The elements in the window being evaluated.
     * @param congestedAreaReport A collector for emitting elements.
     * @throws Exception The function may throw exceptions to fail the program and trigger recovery.
     */
    @Override
    public void apply(TimeWindow window, Iterable<TaxiReport> taxiReports, Collector<CongestedAreaReport> congestedAreaReport) throws Exception {
        double costAvg = 0;
        int numberOfTrips = 0;
        for (TaxiReport report : taxiReports) {
            costAvg += report.getTotalAmount();
            numberOfTrips++;
        }
        if (numberOfTrips == 0) {
            throw new NullPointerException("No taxi report in this window");
        }
        costAvg /= numberOfTrips;
        CongestedAreaReport report = new CongestedAreaReport(new Date(window.getStart()), numberOfTrips, costAvg);
        congestedAreaReport.collect(report);
    }


}
