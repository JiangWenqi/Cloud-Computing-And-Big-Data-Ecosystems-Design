package es.upm.fi.cloud.YellowTaxiTrip;

import es.upm.fi.cloud.YellowTaxiTrip.functions.SaturatedVendorFunction;
import es.upm.fi.cloud.YellowTaxiTrip.functions.SaturatedVendorMapper;
import es.upm.fi.cloud.YellowTaxiTrip.models.SaturatedVendorRecord;
import es.upm.fi.cloud.YellowTaxiTrip.models.TaxiReport;
import es.upm.fi.cloud.YellowTaxiTrip.functions.TaxiReportMapper;
import es.upm.fi.cloud.YellowTaxiTrip.utils.Constants;
import es.upm.fi.cloud.YellowTaxiTrip.utils.FileUtils;
import org.apache.flink.api.java.utils.ParameterTool;
import org.apache.flink.core.fs.FileSystem;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;
import java.util.TimeZone;

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
    private static final Logger LOGGER = LoggerFactory.getLogger(SaturatedVendor.class);

    private static final String INPUT = "input";
    private static final String OUTPUT = "output";

    public static void main(String[] args) throws Exception {
        // 1. Getting the parameters from the command line
        String inputPath;
        String outputPath;
        ParameterTool parameters = ParameterTool.fromArgs(args);
        if (parameters.getNumberOfParameters() != 2) {
            LOGGER.error("Usage: <input path> <output path>");
            throw new IllegalArgumentException("Wrong number of arguments");
        }
        if (parameters.has(INPUT)) {
            inputPath = parameters.get(INPUT);
            LOGGER.info("Input path: {}", inputPath);
            if (!FileUtils.isExist(inputPath)) {
                throw new NullPointerException("The input path is not exist");
            }
        } else {
            throw new IllegalArgumentException("No input path specified");
        }

        if (parameters.has(OUTPUT)) {
            outputPath = parameters.get(OUTPUT);
            FileUtils.recreateFile(outputPath);
            LOGGER.info("Output path: {}", outputPath);
        } else {
            throw new IllegalArgumentException("No output path specified");
        }

        // 2. Setting up the streaming execution environment
        final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        // 3. Reading the raw text from the input path
        DataStream<String> rawFile = env.readTextFile(inputPath);
        // 4. Mapping the raw text to TaxiReport and assigning their event time
        DataStream<TaxiReport> taxiReports = rawFile.filter(line -> !line.isEmpty())
                .map(new TaxiReportMapper(TimeZone.getTimeZone("GMT-11")))
                .filter(Objects::nonNull)
                .assignTimestampsAndWatermarks(Constants.TAXI_REPORT_STRATEGY);

        // 5. Getting the saturated vendor
        DataStream<SaturatedVendorRecord> saturatedVendorRecords = taxiReports.map(new SaturatedVendorMapper())
                .keyBy(SaturatedVendorRecord::getVendorId)
                .countWindow(2, 1)
                .apply(new SaturatedVendorFunction());

        // 6. Writing the results to the output path
        saturatedVendorRecords.writeAsText(outputPath, FileSystem.WriteMode.OVERWRITE)
                .setParallelism(1);

        // 7. Executing the job
        env.execute("SaturatedVendor");
    }
}
