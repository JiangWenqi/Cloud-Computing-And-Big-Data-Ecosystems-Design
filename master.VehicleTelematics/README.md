# How to run it
## 1. Set up Flink environment and start up the local service
### Install Flink: `brew install apache-flink`

### Start/Stop Local Cluster:

```bash

cd /usr/local/Cellar/apache-flink/1.14.0/libexec/bin
./start-cluster.sh

```

## 2. Build Demo and Run it

```bash

cd master.VehicleTelematics
mvn clean package -Pbuild-jar
flink run target/flink-program-1.0-SNAPSHOT-jar-with-dependencies.jar

```

## 3. Check Your Program In Web Page `localhost:8081`

## 4. Stop Local Cluster

```bash

cd /usr/local/Cellar/apache-flink/1.14.0/libexec/bin
./stop-cluster.sh

```

# [Description](./docs/description.md)