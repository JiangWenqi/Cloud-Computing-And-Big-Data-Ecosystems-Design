package es.upm.fi.cloud.YellowTaxiTrip.model;

import org.apache.flink.api.common.functions.MapFunction;

import java.sql.Timestamp;
import java.util.logging.Logger;

/**
 * @author wenqi
 */
public class TaxiReportMapper implements MapFunction<String, TaxiReport> {

    private static final Logger LOGGER = Logger.getLogger(TaxiReportMapper.class.getName());

    @Override
    public TaxiReport map(String line) throws Exception {
        TaxiReport report = new TaxiReport();
        try {
            String[] fields = line.split(",");
            report.setVendorId(Integer.parseInt(fields[0]));
            report.setTpepPickupDatetime(Timestamp.valueOf(fields[1]));
            report.setTpepDropoffDatetime(Timestamp.valueOf(fields[2]));
            report.setPassengerCount(Double.parseDouble(fields[3]));
            report.setTripDistance(Double.parseDouble(fields[4]));
            report.setRateCodeId(Double.parseDouble(fields[5]));
            report.setStoreAndFwdFlag("Y".equals(fields[6]));
            report.setPuLocationId(Integer.parseInt(fields[7]));
            report.setDoLocationId(Integer.parseInt(fields[8]));
            report.setPaymentType(Integer.parseInt(fields[9]));
            report.setFareAmount(Double.parseDouble(fields[10]));
            report.setExtra(Double.parseDouble(fields[11]));
            report.setMtaTax(Double.parseDouble(fields[12]));
            report.setTipAmount(Double.parseDouble(fields[13]));
            report.setTollsAmount(Double.parseDouble(fields[14]));
            report.setImprovementSurcharge(Double.parseDouble(fields[15]));
            report.setTotalAmount(Double.parseDouble(fields[16]));
            report.setCongestionSurcharge(Double.parseDouble(fields[17]));
            report.setAirportFee(Double.parseDouble(fields[18]));
            return report;
        } catch (Exception e) {
            LOGGER.warning("Error in parsing line: " + line);
            return report;
        }

    }
}
