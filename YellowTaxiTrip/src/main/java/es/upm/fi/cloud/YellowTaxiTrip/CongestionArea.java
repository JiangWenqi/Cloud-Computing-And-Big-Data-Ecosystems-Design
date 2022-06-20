package es.upm.fi.cloud.YellowTaxiTrip;

import es.upm.fi.cloud.YellowTaxiTrip.models.CongestedAreaReport;
import es.upm.fi.cloud.YellowTaxiTrip.models.CongestionAreaFunction;
import es.upm.fi.cloud.YellowTaxiTrip.models.TaxiReport;
import es.upm.fi.cloud.YellowTaxiTrip.models.TaxiReportMapper;
import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import org.apache.flink.api.java.utils.ParameterTool;
import org.apache.flink.core.fs.FileSystem;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.windowing.assigners.TumblingEventTimeWindows;
import org.apache.flink.streaming.api.windowing.time.Time;

import java.util.Objects;
import java.util.logging.Logger;

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
 * @author Wenqi
 */
public class CongestionArea {

    private static final Logger LOGGER = Logger.getLogger(CongestionArea.class.getName());


    /**
     * set up the streaming execution environment
     */
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
//                        WatermarkStrategy.<TaxiReport>forBoundedOutOfOrderness(Duration.ofMinutes(5))
                        WatermarkStrategy.<TaxiReport>forMonotonousTimestamps()
                                .withTimestampAssigner((event, timestamp) -> event.getTpepPickupDatetime().getTime())
                );
        // Executing the task
        DataStream<CongestedAreaReport> congestedAreaReports = taxiReports
                .filter(taxiReport -> taxiReport.getCongestionSurcharge() > 0)
                .windowAll(TumblingEventTimeWindows.of(Time.days(1)))
                .apply(new CongestionAreaFunction());
        // Writing the result to the output path
        congestedAreaReports.writeAsText(outputPath, FileSystem.WriteMode.OVERWRITE)
                .setParallelism(1);
        LOGGER.info("Finished the Congestion Area Task !!!");
        env.execute("Congestion Area");
    }
}
