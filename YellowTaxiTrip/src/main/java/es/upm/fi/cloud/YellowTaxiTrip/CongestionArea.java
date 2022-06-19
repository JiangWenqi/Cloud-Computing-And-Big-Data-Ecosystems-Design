package es.upm.fi.cloud.YellowTaxiTrip;

import es.upm.fi.cloud.YellowTaxiTrip.model.TaxiReport;
import es.upm.fi.cloud.YellowTaxiTrip.model.TaxiReportMapper;
import org.apache.flink.api.common.io.FileInputFormat;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.logging.Logger;

/**
 * @author Wenqi
 */
public class CongestionArea {

    private static final Logger LOGGER = Logger.getLogger(CongestionArea.class.getName());
    private static final String INPUT_PATH = "file:///Users/wenqi/Projects/Study/Cloud-Computing-And-Big-Data-Ecosystems-Design/YellowTaxiTrip/src/main/resources/yellow_tripdata_2022-03.csv";

    /**
     * set up the streaming execution environment
     */
    public static void main(String[] args) throws Exception {
        final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        DataStream<String> rawFile = env.readTextFile(INPUT_PATH);
        DataStream<TaxiReport> taxiReports = rawFile
                .filter(line -> !line.isEmpty())
                .map(new TaxiReportMapper());

        taxiReports.print();

        env.execute("Congestion Area");
    }
}
