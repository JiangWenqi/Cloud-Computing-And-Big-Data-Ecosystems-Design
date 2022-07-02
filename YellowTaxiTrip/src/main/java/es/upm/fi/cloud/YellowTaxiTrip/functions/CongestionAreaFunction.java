package es.upm.fi.cloud.YellowTaxiTrip.functions;

import es.upm.fi.cloud.YellowTaxiTrip.models.CongestedAreaRecord;
import es.upm.fi.cloud.YellowTaxiTrip.models.TaxiReport;
import org.apache.flink.streaming.api.functions.windowing.AllWindowFunction;
import org.apache.flink.streaming.api.windowing.windows.TimeWindow;
import org.apache.flink.util.Collector;

import java.util.Date;

/**
 * @author Wenqi Jiang & Zhuo Cheng
 */
public class CongestionAreaFunction implements AllWindowFunction<TaxiReport, CongestedAreaRecord, TimeWindow> {
    /**
     * @param window              The window that is being evaluated.
     * @param taxiReports         The tax reports in the window being evaluated.
     * @param congestedAreaReport A collector for emitting congested area report.
     * @throws Exception The function may throw exceptions to fail the program and trigger recovery.
     */
    @Override
    public void apply(TimeWindow window, Iterable<TaxiReport> taxiReports, Collector<CongestedAreaRecord> congestedAreaReport) throws Exception {
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
        CongestedAreaRecord report = new CongestedAreaRecord(new Date(window.getStart()), numberOfTrips, costAvg);
        congestedAreaReport.collect(report);
    }


}
