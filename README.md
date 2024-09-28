# Kafka Starter Project

This project serves as a starting point for learning and using **Kafka**, **Scala** (via Maven with `scala-maven-plugin`), **HOCON** configurations, **logging** with **SLF4J** and **Log4j2**, and containerization with **Docker**. It demonstrates a simple producer-consumer setup with Kafka, where messages can be published to a Kafka topic and consumed in real-time.

## Features

- **Kafka Integration**: A basic setup of a Kafka producer and consumer.
- **Scala with Maven**: Using `scala-maven-plugin` to compile and run Scala code.
- **HOCON Configuration**: Configuration management using HOCON files (`.conf`).
- **Logging**: Structured logging using SLF4J with Log4j2 backend.
- **Docker**: Kafka instance and Zookeeper are managed through Docker.

## Prerequisites

Make sure you have the following installed:

- **Docker** (for running Kafka)
- **Java 8+**
- **Maven** (for managing dependencies and building the Scala application)

## Installation and Setup

Follow these steps to set up and run the project locally.

### Step 1: Run Kafka using Docker

You will need to spin up Kafka in Docker to use the producer and consumer.

1. Start a docker Kafka component with the following docker compose file: [docker-compose.yml](docker/kafka-instance/docker-compose.yml) 

```bash
docker-compose up -d
```

This will start a Docker container, exposing Kafka on `localhost:9092`.

### Step 2: Run the Consumer App

The consumer will be listening for messages on the configured Kafka topic.

### Step 3: Run the Producer App to Send Messages

Now, run the main application which acts as a Kafka producer and allows you to send messages.

You can input messages through the terminal, and they will be sent to the Kafka topic. The consumer running in Step 2 will consume these messages and print them to the console.

## Usage

### Send and Consume Messages

1. After starting the Kafka producer (the `App` class), you will be prompted to enter messages in the terminal.
2. Each message you enter will be sent to the Kafka topic.
3. The consumer (`ConsumerApp`) will immediately consume the messages and print them to the console.

### Sample Interaction

**Producer Terminal:**

```
Enter message key: key
Enter message value: sample message!
You entered Message{ key: 'key', value: 'sample message!' }. Ready to send? (y/n) y
16:43:08 INFO [MessageProducer] - Message delivered to topic test-topic at offset 8349792
```

**Consumer Terminal:**

```
16:43:08 INFO [MessageConsumer] - Processing Message{ key: 'key', value: 'sample message!' }
```

## Configuration

This project uses **HOCON** files for configuration. You can find the configuration files in the `src/main/resources/application.conf` file. Key settings include:

- Kafka broker settings
- Topic configuration
- Kafka serialization / deserialization settings

Modify the `application.conf` file to suit your environment or use case.

## Logging

Logging is handled through **Log4j2**. The logging configuration can be modified in the `log4j2.xml` file located under `src/main/resources/`.

## Conclusion

This project provides a simple but effective starting point for working with Kafka in a Scala environment. It includes configuration management, logging, and Docker setup to make it easy to experiment with Kafka-based messaging systems.
