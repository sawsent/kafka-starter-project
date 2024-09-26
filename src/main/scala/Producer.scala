import com.typesafe.config.ConfigFactory
import org.apache.kafka.clients.producer.{Callback, KafkaProducer, ProducerRecord, RecordMetadata}

import java.util.Properties

object Producer {
  def apply(topic: String, props: Properties): Producer[String, String] = {
    new Producer[String, String](topic, props)
  }
}

class Producer[K, V](val topic: String, val props: Properties) {
  val producer = new KafkaProducer[K, V](props)

  def publish(key: K, value: V) = {
    val record = new ProducerRecord[K, V](topic, key, value)

    producer.send(record, (metadata: RecordMetadata, exception: Exception) => {
      Option(exception).getOrElse(println(s"Message delivered to topic ${metadata.topic()} at offset ${metadata.offset()}"))
      println(s"Error sending message: ${exception.getMessage}")
    })
    producer.flush()
  }

  def publish(key: K, value: V, callback: Callback) = {
    val record = new ProducerRecord[K, V](topic, key, value)
    producer.send(record, callback)
    producer.flush()
  }

  def close() = producer.close()

}
