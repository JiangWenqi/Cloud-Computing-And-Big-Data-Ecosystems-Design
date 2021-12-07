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
import org.apache.flink.api.common.serialization.SimpleStringEncoder;
import org.apache.flink.api.java.io.CsvInputFormat;
import org.apache.flink.api.java.io.PojoCsvInputFormat;
import org.apache.flink.api.java.typeutils.PojoTypeInfo;
import org.apache.flink.api.java.typeutils.TypeExtractor;
import org.apache.flink.core.fs.Path;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.sink.filesystem.StreamingFileSink;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.time.Duration;
import java.util.Properties;


/**
 * @author Wenqi Jiang & Zhou
 */
public class VehicleTelematics {

    private static final Logger LOGGER = LogManager.getLogger(VehicleTelematics.class);

    private static final Properties DEFAULT_PARAMETERS = new ConfigUtil("parameters.properties").getProperties();

    public static void main(String[] args) {

        File inputFile = new File(DEFAULT_PARAMETERS.getProperty("input.file"));

        // set up the streaming execution environment
        final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        // read raw data from local csv
        Path inputFilePath = Path.fromLocalFile(inputFile);
        PojoTypeInfo<VehicleReport> pojoType = (PojoTypeInfo<VehicleReport>) TypeExtractor.createTypeInfo(VehicleReport.class);
        // Ensure the order after pojo serialization
        String[] fieldOrder = new String[]{"timestamp", "vehicleId", "speed", "xWay", "lane", "direction", "segment", "position"};
        CsvInputFormat<VehicleReport> csvInput = new PojoCsvInputFormat<>(inputFilePath, pojoType, fieldOrder);
        // real-time data
        DataStreamSource<VehicleReport> vehicleReports = env.createInput(csvInput, pojoType);
        // add watermark = currentMaxTimestamp - 10
        WatermarkStrategy<VehicleReport> watermarkStrategy = WatermarkStrategy
                .<VehicleReport>forBoundedOutOfOrderness(Duration.ofSeconds(10))
                .withTimestampAssigner((event, timestamp) -> event.timestamp);
        vehicleReports.assignTimestampsAndWatermarks(watermarkStrategy);

        try {
            SingleOutputStreamOperator<SpeedFine> speedFines = vehicleReports.flatMap(new SpeedRadar());
            speedFines.print("[speed fines]");
            env.execute("Vehicle Telematics");
        } catch (Exception e) {
            LOGGER.error("start error: {}", e.getMessage());
            e.printStackTrace();
        }

    }

}
