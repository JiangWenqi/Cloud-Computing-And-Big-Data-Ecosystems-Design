/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package master;

import master.events.AccidentReport;
import master.events.AvgSpeedFine;
import master.events.SpeedFine;
import master.events.VehicleReport;
import master.functions.AccidentReporter;
import master.functions.AverageSpeedController;
import master.functions.SpeedRadar;
import master.utils.ConfigUtil;
import master.utils.Constants;
import org.apache.flink.api.java.io.CsvInputFormat;
import org.apache.flink.api.java.io.PojoCsvInputFormat;
import org.apache.flink.api.java.typeutils.PojoTypeInfo;
import org.apache.flink.api.java.typeutils.TypeExtractor;
import org.apache.flink.core.fs.FileSystem.WriteMode;
import org.apache.flink.core.fs.Path;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

import java.util.Properties;

/** @author Wenqi Jiang & Zhuo Cheng */
public class VehicleTelematics {

  private static final Properties DEFAULT_PARAMETERS = new ConfigUtil().getProperties();
  private static final String SPEED_FINES_FILENAME =
      DEFAULT_PARAMETERS.getProperty("speed.fines.file");
  private static final String AVGSPEED_FINES_FILENAME =
      DEFAULT_PARAMETERS.getProperty("avgspeed.fines.file");
  private static final String ACCIDENTS_FILENAME = DEFAULT_PARAMETERS.getProperty("accidents.file");

  public static void main(String[] args) throws Exception {
    // ------------------ read input file & output folder ----------------------------
    String inputFile;
    String outputFolder;
    if (args.length == 2) {
      inputFile = args[0];
      outputFolder = args[1];
    } else {
      throw new NullPointerException(
          "Please check you command, make sure it has $PATH_TO_INPUT_FILE $PATH_TO_OUTPUT_FOLDER");
    }

    // set up the streaming execution environment
    final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
    // ------------------- read raw data from a local csv -------------------------------
    Path inputFilePath = new Path(inputFile);
    PojoTypeInfo<VehicleReport> pojoType =
        (PojoTypeInfo<VehicleReport>) TypeExtractor.createTypeInfo(VehicleReport.class);
    CsvInputFormat<VehicleReport> csvInput =
        new PojoCsvInputFormat<>(inputFilePath, pojoType, Constants.VEHICLE_REPORT_FIELDS);
    DataStream<VehicleReport> vehicleReports = env.createInput(csvInput, pojoType);

    // ----------------------------- calculation --------------------------------
    DataStream<SpeedFine> speedFines = SpeedRadar.issueFines(vehicleReports);
    DataStream<AvgSpeedFine> avgSpeedFines = AverageSpeedController.issueFines(vehicleReports);
    DataStream<AccidentReport> accidentReports = AccidentReporter.report(vehicleReports);

    // ---------------------------- save result ------------------------------------
    speedFines
        .writeAsText(outputFolder + SPEED_FINES_FILENAME, WriteMode.OVERWRITE)
        .setParallelism(1);
    avgSpeedFines
        .writeAsText(outputFolder + AVGSPEED_FINES_FILENAME, WriteMode.OVERWRITE)
        .setParallelism(1);
    accidentReports
        .writeAsText(outputFolder + ACCIDENTS_FILENAME, WriteMode.OVERWRITE)
        .setParallelism(1);

    env.execute("Vehicle Telematics");
  }
}
