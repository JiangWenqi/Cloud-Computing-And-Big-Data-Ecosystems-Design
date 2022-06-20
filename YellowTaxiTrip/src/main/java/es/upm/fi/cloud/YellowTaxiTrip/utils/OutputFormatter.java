package es.upm.fi.cloud.YellowTaxiTrip.utils;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author wenqi
 */
public class OutputFormatter {
    private SimpleDateFormat dateFormat;
    private DecimalFormat decimalFormat;

    public OutputFormatter() {
        dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        decimalFormat = new DecimalFormat("##.00");
    }

    public String dateFormat(Date date) {
        return dateFormat.format(date);
    }

    public String decimalFormat(double d) {
        return decimalFormat.format(d);
    }

}
