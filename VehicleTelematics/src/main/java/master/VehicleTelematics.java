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
import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import org.apache.flink.api.java.io.CsvInputFormat;
import org.apache.flink.api.java.io.PojoCsvInputFormat;
import org.apache.flink.api.java.typeutils.PojoTypeInfo;
import org.apache.flink.api.java.typeutils.TypeExtractor;
import org.apache.flink.core.fs.Path;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

import java.io.File;
import java.time.Duration;
import java.util.Properties;

/** @author Wenqi Jiang */
public class VehicleTelematics {

  private static final Properties DEFAULT_PARAMETERS = new ConfigUtil().getProperties();

  public static void main(String[] args) throws Exception {
    // set up the streaming execution environment
    final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

    // read raw data from local csv
    File inputFile = new File(DEFAULT_PARAMETERS.getProperty("input.file"));
    Path inputFilePath = Path.fromLocalFile(inputFile);
    PojoTypeInfo<VehicleReport> pojoType =
        (PojoTypeInfo<VehicleReport>) TypeExtractor.createTypeInfo(VehicleReport.class);
    // Ensure the order after pojo serialization
    String[] fieldOrder =
        new String[] {
          "timestamp", "vehicleId", "speed", "xWay", "lane", "direction", "segment", "position"
        };
    CsvInputFormat<VehicleReport> csvInput =
        new PojoCsvInputFormat<>(inputFilePath, pojoType, fieldOrder);

    // real-time data
    SingleOutputStreamOperator<VehicleReport> vehicleReports = env.createInput(csvInput, pojoType);

    vehicleReports.assignTimestampsAndWatermarks(reportWatermark(Duration.ofSeconds(40)));

    SingleOutputStreamOperator<SpeedFine> speedFines = vehicleReports.flatMap(new SpeedRadar());
    speedFines.print("speed fine");
    env.execute("Vehicle Telematics");
  }

  /** @return watermark strategy */
  private static WatermarkStrategy<VehicleReport> reportWatermark(Duration maxOutOfOrderness) {
    // add watermark = currentMaxTimestamp - 10
    return WatermarkStrategy.<VehicleReport>forBoundedOutOfOrderness(maxOutOfOrderness)
        .withTimestampAssigner((event, timestamp) -> event.timestamp * 1000);
  }
}
