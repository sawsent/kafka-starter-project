package app

import config.KafkaConfig
import consumer.MessageConsumer
import logging.GlobalLogging

object ConsumerApp extends App with GlobalLogging {
  val topic = KafkaConfig.TOPIC
  val consumer = MessageConsumer(topic, KafkaConfig.consumerProperties)
  logger.info(s"Consumer is running and listening on topic '$topic''")
  consumer.consumeMessages()
}
