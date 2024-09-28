package producer

import MessageProducer.logger
import logging.LoggerFactory
import model.Message
import org.apache.kafka.clients.producer.{Callback, KafkaProducer, ProducerRecord, RecordMetadata}
import org.apache.logging.log4j.Logger

import java.util.Properties

object MessageProducer {
  private val logger: Logger = LoggerFactory("MessageProducer")

  def apply(topic: String, props: Properties): MessageProducer = {
    new MessageProducer(topic, props)
  }
}

class MessageProducer(val topic: String, val props: Properties) {

  private val producer = new KafkaProducer[String, String](props)

  def publish(msg: Message[String, String]): Unit = {
    publish(msg, (metadata: RecordMetadata, ex: Exception) => ex match {
      case null => logger.info(s"Message delivered to topic ${metadata.topic()} at offset ${metadata.offset()}")
      case _ => logger.warn(s"Error sending message: ${ex.getMessage}")
    })
  }

  def publish(msg: Message[String, String], callback: Callback): Unit = {
    val record = new ProducerRecord[String, String](topic, msg.key, msg.value)
    producer.send(record, callback)
    producer.flush()
  }

  def close(): Unit = producer.close()

}
