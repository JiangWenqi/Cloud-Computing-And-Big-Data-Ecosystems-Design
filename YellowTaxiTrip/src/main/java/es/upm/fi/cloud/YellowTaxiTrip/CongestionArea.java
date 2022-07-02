package es.upm.fi.cloud.YellowTaxiTrip;

import es.upm.fi.cloud.YellowTaxiTrip.models.CongestedAreaRecord;
import es.upm.fi.cloud.YellowTaxiTrip.functions.CongestionAreaFunction;
import es.upm.fi.cloud.YellowTaxiTrip.models.TaxiReport;
import es.upm.fi.cloud.YellowTaxiTrip.functions.TaxiReportMapper;
import es.upm.fi.cloud.YellowTaxiTrip.utils.Constants;
import es.upm.fi.cloud.YellowTaxiTrip.utils.FileUtils;
import org.apache.flink.api.java.utils.ParameterTool;
import org.apache.flink.core.fs.FileSystem;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.windowing.assigners.TumblingEventTimeWindows;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;
import java.util.TimeZone;

/**
 * The commission wants to control the number of taxis accessing the most congested areas of the city,
 * more concretely the commission is interested in knowing for each day,
 * the number of trips accessing these areas and the average cost of the trips that go through these areas.
 * <p>
 * 1. Pickup time is the timestamp we used
 * 2. A taxi went through a congested area, if the congestion_surcharge field was greater than 0
 * 3. total_amount is the total amount charged of the trip (The cost is independent of the payment type)
 * 4. This is a data streaming exercise
 * 5. The records are available because otherwise it is difficult for you to test the results.
 *
 * @author Wenqi Jiang & Zhuo Cheng
 */
public class CongestionArea {

    private static final Logger LOGGER = LoggerFactory.getLogger(CongestionArea.class);

    private static final String INPUT = "input";
    private static final String OUTPUT = "output";

    /**
     * set up the streaming execution environment
     */
    public static void main(String[] args) throws Exception {
        // 1. Getting the parameters from the command line and check if the parameters are correct
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
        DataStream<TaxiReport> taxiReports = rawFile
                // filter empty string
                .filter(line -> !line.isEmpty())
                // transfer raw string records to format taxi records and set the time zone to "GMT+2"
                .map(new TaxiReportMapper(TimeZone.getTimeZone("GMT+2")))
                // filter empty records
                .filter(Objects::nonNull)
                // set the event timestamp of records
                .assignTimestampsAndWatermarks(Constants.TAXI_REPORT_STRATEGY);
        // 5. Calculating the number of taxis that went through a congested area
        DataStream<CongestedAreaRecord> congestedAreaRecords = taxiReports
                // A taxi went through a congested area, if the congestion_surcharge field was greater than 0
                .filter(taxiReport -> taxiReport.getCongestionSurcharge() > 0)
                // time windows is a day
                .windowAll(TumblingEventTimeWindows.of(Time.days(1)))
                // calculate the result
                .apply(new CongestionAreaFunction());

        // 6. Writing the result to the output path, the parallelism for the write operation to the output files must be 1.
        congestedAreaRecords.writeAsText(outputPath, FileSystem.WriteMode.OVERWRITE)
                .setParallelism(1);
        env.execute("Congestion Area");
    }
}
