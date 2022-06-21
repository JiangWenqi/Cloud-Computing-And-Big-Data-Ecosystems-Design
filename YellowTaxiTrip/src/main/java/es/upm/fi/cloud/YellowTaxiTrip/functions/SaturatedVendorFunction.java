package es.upm.fi.cloud.YellowTaxiTrip.functions;

import es.upm.fi.cloud.YellowTaxiTrip.models.SaturatedVendorRecord;
import org.apache.flink.streaming.api.functions.windowing.WindowFunction;
import org.apache.flink.streaming.api.windowing.windows.GlobalWindow;
import org.apache.flink.util.Collector;

import java.util.Iterator;

/**
 * @author Wenqi
 */
public class SaturatedVendorFunction implements WindowFunction<SaturatedVendorRecord, SaturatedVendorRecord, Integer, GlobalWindow> {

    @Override
    public void apply(Integer vendorId, GlobalWindow window, Iterable<SaturatedVendorRecord> in, Collector<SaturatedVendorRecord> out) throws Exception {
        Iterator<SaturatedVendorRecord> iterator = in.iterator();
        SaturatedVendorRecord first = iterator.next();
        if (iterator.hasNext()) {
            SaturatedVendorRecord second = iterator.next();
            int trips = first.getNumberOfTrips() + second.getNumberOfTrips();
            // A vendor is saturated when a trip starts in less than 10 minutes after the previous trip finished.
            long diff = second.getStartTime().getTime() - first.getFinishTime().getTime();
            int tenMinutes = 10 * 60 * 1000;
            if (diff < tenMinutes) {
                out.collect(new SaturatedVendorRecord(vendorId, first.getStartTime(), second.getFinishTime(), trips));
            }
        }
    }
}
