package producer

import logging.GlobalLogging
import model.Message
import org.apache.kafka.clients.producer.{Callback, KafkaProducer, ProducerRecord, RecordMetadata}

import java.util.Properties

object MessageProducer {

  def apply(topic: String, props: Properties): MessageProducer = {
    new MessageProducer(topic, props)
  }
}

class MessageProducer(val topic: String, val props: Properties) extends GlobalLogging {

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
