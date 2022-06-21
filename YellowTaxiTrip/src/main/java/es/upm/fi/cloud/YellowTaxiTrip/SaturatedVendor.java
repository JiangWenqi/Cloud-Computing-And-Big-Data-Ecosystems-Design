package es.upm.fi.cloud.YellowTaxiTrip;

import es.upm.fi.cloud.YellowTaxiTrip.functions.SaturatedVendorFunction;
import es.upm.fi.cloud.YellowTaxiTrip.functions.SaturatedVendorMapper;
import es.upm.fi.cloud.YellowTaxiTrip.models.SaturatedVendorRecord;
import es.upm.fi.cloud.YellowTaxiTrip.models.TaxiReport;
import es.upm.fi.cloud.YellowTaxiTrip.functions.TaxiReportMapper;
import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import org.apache.flink.api.java.utils.ParameterTool;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.windowing.assigners.GlobalWindows;

import java.time.Duration;
import java.util.Objects;

/**
 * The commission wants to register when a vendorID is saturated.
 * A vendor is saturated when a trip starts in less than 10 minutes after the previous trip finished.
 * If there are two or more consecutive trips from a vendor (a trip starts in less than 10 minutes after the previous trip finished),
 * a tuple is generated with this information :
 * vendorID, start of the first trip (tpep_pickup_datetime), finish time of the last trip, total number of consecutive trips.
 *
 * @author wenqi
 */
public class SaturatedVendor {
    public static void main(String[] args) throws Exception {
        // Getting the parameters from the command line
        String inputPath;
        String outputPath;
        ParameterTool parameters = ParameterTool.fromArgs(args);
        if (parameters.has("input")) {
            inputPath = parameters.get("input");
        } else {
            throw new IllegalArgumentException("No input path specified");
        }
        if (parameters.has("output")) {
            outputPath = parameters.get("output");
        } else {
            throw new IllegalArgumentException("No output path specified");
        }

        // Setting up the streaming execution environment
        final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        // Reading the raw text from the input path
        DataStream<String> rawFile = env.readTextFile(inputPath);
        // Mapping the raw text to TaxiReport and assigning their event time
        DataStream<TaxiReport> taxiReports = rawFile.filter(line -> !line.isEmpty())
                .map(new TaxiReportMapper())
                .filter(Objects::nonNull)
                .assignTimestampsAndWatermarks(
                        WatermarkStrategy.<TaxiReport>forMonotonousTimestamps()
                                .withTimestampAssigner((taxiReport, timestamp) -> taxiReport.getTpepPickupDatetime().getTime())
                );


        // Grouping the taxiReports by vendorID and windowing them by 10 minutes
        DataStream<SaturatedVendorRecord> saturatedVendorRecords = taxiReports.map(new SaturatedVendorMapper())
                .keyBy(SaturatedVendorRecord::getVendorId)
                .countWindow(2, 1)
                .apply(new SaturatedVendorFunction());

        saturatedVendorRecords.print();
        env.execute("SaturatedVendor");
    }
}
