package master.functions;

import master.utils.ConfigUtil;
import org.apache.flink.api.common.serialization.SimpleStringEncoder;
import org.apache.flink.configuration.MemorySize;
import org.apache.flink.connector.file.sink.FileSink;
import org.apache.flink.core.fs.Path;
import org.apache.flink.streaming.api.functions.sink.filesystem.OutputFileConfig;
import org.apache.flink.streaming.api.functions.sink.filesystem.RollingPolicy;
import org.apache.flink.streaming.api.functions.sink.filesystem.rollingpolicies.DefaultRollingPolicy;

import java.util.concurrent.TimeUnit;


/**
 * TODO need  implements Sink<x,x,x,x>
 * @author Vinci
 */
public class CsvSink<T> {
    private static Path path;

    public CsvSink(String folder) {
        path = new Path(folder);
    }

    public CsvSink() {
        path = new Path(new ConfigUtil().getProperty("input.path"));
    }

    public FileSink<T> csvSinker() {
        return FileSink.forRowFormat(
                        path,
                        new SimpleStringEncoder<T>("UTF-8"))
                .withRollingPolicy(rollingPolicy())
                .withOutputFileConfig(outputFileConfig())
                .build();
    }

    private OutputFileConfig outputFileConfig() {
        return OutputFileConfig.builder().withPartPrefix("speed-fine").withPartSuffix(".csv").build();
    }

    private RollingPolicy<T, String> rollingPolicy() {
        return DefaultRollingPolicy.builder()
                // default 60s
                .withRolloverInterval(TimeUnit.SECONDS.toMillis(30))
                // default 60s
                .withInactivityInterval(TimeUnit.SECONDS.toMillis(5))
                // default 128M
                .withMaxPartSize(MemorySize.ofMebiBytes(1).getBytes())
                .build();
    }
}
