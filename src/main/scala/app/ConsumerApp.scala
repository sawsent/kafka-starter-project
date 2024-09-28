package app

import config.KafkaConfig
import consumer.MessageConsumer

object ConsumerApp extends App {
  val topic = KafkaConfig.TOPIC
  val consumer = MessageConsumer(topic, KafkaConfig.consumerProperties)
  println(s"Consumer is running and listening on topic '$topic''")
  consumer.consumeMessages()
}
