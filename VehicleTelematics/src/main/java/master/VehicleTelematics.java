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

import master.events.SpeedFine;
import master.events.VehicleReport;
import master.functions.SpeedRadar;
import master.utils.ConfigUtil;
import master.utils.Constants;
import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import org.apache.flink.api.common.functions.FilterFunction;
import org.apache.flink.api.common.functions.ReduceFunction;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.api.java.functions.KeySelector;
import org.apache.flink.api.java.io.CsvInputFormat;
import org.apache.flink.api.java.io.PojoCsvInputFormat;
import org.apache.flink.api.java.typeutils.PojoTypeInfo;
import org.apache.flink.api.java.typeutils.TypeExtractor;
import org.apache.flink.core.fs.FileSystem.WriteMode;
import org.apache.flink.core.fs.Path;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.windowing.assigners.EventTimeSessionWindows;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.Duration;
import java.util.Properties;

/** @author Wenqi Jiang */
public class VehicleTelematics {

  private static final Properties DEFAULT_PARAMETERS = new ConfigUtil().getProperties();
  private static final Logger LOGGER = LogManager.getLogger(VehicleTelematics.class);

  public static void main(String[] args) throws Exception {
    // set up the streaming execution environment
    final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
    // read raw data from local csv
    Path inputFilePath = new Path(DEFAULT_PARAMETERS.getProperty("input.file"));
    LOGGER.info("Load vehicle report from {}", inputFilePath);
    PojoTypeInfo<VehicleReport> pojoType =
        (PojoTypeInfo<VehicleReport>) TypeExtractor.createTypeInfo(VehicleReport.class);
    CsvInputFormat<VehicleReport> csvInput =
        new PojoCsvInputFormat<>(inputFilePath, pojoType, Constants.VEHICLE_REPORT_FIELDS);
    // real-time data
    DataStream<VehicleReport> vehicleReports = env.createInput(csvInput, pojoType);
    vehicleReports.assignTimestampsAndWatermarks(reportWatermark(Duration.ofSeconds(40)));

    // -------------------- speed fines ----------------------------------------
    DataStream<SpeedFine> speedFines = SpeedRadar.issueSpeedFines(vehicleReports);
    speedFines
        .writeAsText(DEFAULT_PARAMETERS.getProperty("output.file"), WriteMode.OVERWRITE)
        .setParallelism(1);




    env.execute("Vehicle Telematics");
  }

  /** @return watermark strategy */
  private static WatermarkStrategy<VehicleReport> reportWatermark(Duration maxOutOfOrderness) {
    // add watermark = currentMaxTimestamp - maxOutOfOrderness
    return WatermarkStrategy.<VehicleReport>forBoundedOutOfOrderness(maxOutOfOrderness)
        .withTimestampAssigner((event, timestamp) -> event.timestamp * 1000);
  }
}
