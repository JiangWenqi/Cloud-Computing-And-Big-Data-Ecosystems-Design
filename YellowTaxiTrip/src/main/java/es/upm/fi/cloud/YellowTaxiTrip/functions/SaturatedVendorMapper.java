package es.upm.fi.cloud.YellowTaxiTrip.functions;

import es.upm.fi.cloud.YellowTaxiTrip.SaturatedVendor;
import es.upm.fi.cloud.YellowTaxiTrip.models.SaturatedVendorRecord;
import es.upm.fi.cloud.YellowTaxiTrip.models.TaxiReport;
import org.apache.flink.api.common.functions.MapFunction;

/**
 * @author wenqi
 */
public class SaturatedVendorMapper implements MapFunction<TaxiReport, SaturatedVendorRecord> {
    /**
     * The mapping method. Takes an element from the input data set and transforms it into exactly
     * one element.
     *
     * @param taxiReport The input value.
     * @return The transformed value
     * @throws Exception This method may throw exceptions. Throwing an exception will cause the
     *                   operation to fail and may trigger recovery.
     */
    @Override
    public SaturatedVendorRecord map(TaxiReport taxiReport) throws Exception {
        return new SaturatedVendorRecord(taxiReport.getVendorId(), taxiReport.getTpepPickupDatetime(), taxiReport.getTpepDropoffDatetime(), 1);
    }
}
