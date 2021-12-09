package master;

import master.events.SpeedFine;
import master.events.VehicleReport;
import master.functions.SpeedRadar;
import master.utils.ConfigUtil;
import master.utils.Constants;
import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.core.fs.FileSystem.WriteMode;

import java.util.Properties;

/** @author Wenqi Jiang & Zhuo Cheng */
@Deprecated
public class BatchVehicleTelematics {

  private static final Properties DEFAULT_PARAMETERS = new ConfigUtil().getProperties();

  public static void main(String[] args) throws Exception {
    final ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();
    DataSet<VehicleReport> vehicleReports =
        env.readCsvFile(DEFAULT_PARAMETERS.getProperty("input.file"))
            .pojoType(VehicleReport.class, Constants.VEHICLE_REPORT_FIELDS);


//    DataSet<SpeedFine> speedFines = vehicleReports.flatMap(new SpeedRadarFlatMap());
//    speedFines
//        .writeAsText(DEFAULT_PARAMETERS.getProperty("output.file"), WriteMode.OVERWRITE)
//        .setParallelism(1);

    env.execute("batch vehicle telematics");
  }
}
