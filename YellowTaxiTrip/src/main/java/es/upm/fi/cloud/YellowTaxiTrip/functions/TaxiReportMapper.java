package es.upm.fi.cloud.YellowTaxiTrip.functions;

import es.upm.fi.cloud.YellowTaxiTrip.models.TaxiReport;
import org.apache.flink.api.common.functions.MapFunction;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import java.util.logging.Logger;

/**
 * @author wenqi
 */
public class TaxiReportMapper implements MapFunction<String, TaxiReport> {

    private static final Logger LOGGER = Logger.getLogger(TaxiReportMapper.class.getName());

    private Date parseDate(String dateString) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        formatter.setTimeZone(TimeZone.getTimeZone("GMT+2"));
        return formatter.parse(dateString);
    }

    @Override
    public TaxiReport map(String line) throws Exception {
        TaxiReport report = new TaxiReport();
        try {
            String[] fields = line.split(",", -1);
            if (fields[0] != null && fields[0].length() > 0) {
                report.setVendorId(Integer.parseInt(fields[0]));
            }
            if (fields[1] != null && fields[1].length() > 0) {
                report.setTpepPickupDatetime(parseDate(fields[1]));
            }
            if (fields[2] != null && fields[2].length() > 0) {
                report.setTpepDropoffDatetime(parseDate(fields[2]));
            }
            if (fields[3] != null && fields[3].length() > 0) {
                report.setPassengerCount(Double.parseDouble(fields[3]));
            }
            if (fields[4] != null && fields[4].length() > 0) {
                report.setTripDistance(Double.parseDouble(fields[4]));
            }
            if (fields[5] != null && fields[5].length() > 0) {
                report.setRateCodeId(Double.parseDouble(fields[5]));
            }
            if (fields[6] != null && fields[6].length() > 0) {
                report.setStoreAndFwdFlag("Y".equals(fields[6]));
            }
            if (fields[7] != null && fields[7].length() > 0) {
                report.setPuLocationId(Integer.parseInt(fields[7]));
            }
            if (fields[8] != null && fields[8].length() > 0) {
                report.setDoLocationId(Integer.parseInt(fields[8]));
            }
            if (fields[9] != null && fields[9].length() > 0) {
                report.setPaymentType(Integer.parseInt(fields[9]));
            }
            if (fields[10] != null && fields[10].length() > 0) {
                report.setFareAmount(Double.parseDouble(fields[10]));
            }
            if (fields[11] != null && fields[11].length() > 0) {
                report.setExtra(Double.parseDouble(fields[11]));
            }
            if (fields[12] != null && fields[12].length() > 0) {
                report.setMtaTax(Double.parseDouble(fields[12]));
            }
            if (fields[13] != null && fields[13].length() > 0) {
                report.setTipAmount(Double.parseDouble(fields[13]));
            }
            if (fields[14] != null && fields[14].length() > 0) {
                report.setTollsAmount(Double.parseDouble(fields[14]));
            }
            if (fields[15] != null && fields[15].length() > 0) {
                report.setImprovementSurcharge(Double.parseDouble(fields[15]));
            }
            if (fields[16] != null && fields[16].length() > 0) {
                report.setTotalAmount(Double.parseDouble(fields[16]));
            }

            if (fields[17] != null && fields[17].length() > 0) {
                report.setCongestionSurcharge(Double.parseDouble(fields[17]));
            }
            if (fields[18] != null && fields[18].length() > 0) {
                report.setAirportFee(Double.parseDouble(fields[18]));
            }
            return report;
        } catch (Exception e) {
            LOGGER.warning("Error in parsing the line: " + line);
            LOGGER.warning(e.toString());
            return null;
        }
    }
}
