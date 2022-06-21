package es.upm.fi.cloud.YellowTaxiTrip.utils;

import es.upm.fi.cloud.YellowTaxiTrip.models.TaxiReport;
import org.apache.flink.api.common.eventtime.WatermarkStrategy;

/**
 * @author wenqi
 */
public class Constants {
    /**
     * The watermark strategy for the taxi report stream.
     */
    public static final WatermarkStrategy<TaxiReport> TAXI_REPORT_STRATEGY = WatermarkStrategy.<TaxiReport>forMonotonousTimestamps()
            .withTimestampAssigner((event, timestamp) -> event.getTpepPickupDatetime().getTime());

    private Constants() {
    }
}
