# How to run it
## 1. Set up Flink environment and start up the local service
### Install Flink: `brew install apache-flink`

#### Change some config to increase resources for our task
```bash
cd /usr/local/Cellar/apache-flink/1.14.0/libexec/conf
vim flink-flink-conf.yaml
```

increase the number of `taskmanager.numberOfTaskSlots` from `1` to `4`

### Start/Stop Local Cluster:

```bash

cd /usr/local/Cellar/apache-flink/1.14.0/libexec/bin
./start-cluster.sh

```

#### Check Flink log: `/usr/local/Cellar/apache-flink/1.14.0/libexec/log`

## 2. Build Demo and Run it

```bash

cd VehicleTelematics
mvn clean package -Pbuild-jar
flink run -p 3 -c master.VehicleTelematics target/VehicleTelematics-1.0-SNAPSHOT.jar $PATH_TO_INPUT_FILE $PATH_TO_OUTPUT_FOLDER
```

## 3. Check Your Program In Web Page `localhost:8081`

## 4. Stop Local Cluster

```bash

cd /usr/local/Cellar/apache-flink/1.14.0/libexec/bin
./stop-cluster.sh

```

# [Description](./docs/README.md)