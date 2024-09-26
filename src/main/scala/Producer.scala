import com.typesafe.config.ConfigFactory
import org.apache.kafka.clients.producer.{Callback, KafkaProducer, ProducerRecord, RecordMetadata}

import java.util.Properties

object Producer {
  private val config = ConfigFactory.load("application.conf")

  private val brokers: String = config.getString("kafka.common.brokers")
  private val keySerializer: String = config.getString("kafka.producer.serializer.key")
  private val valueSerializer = config.getString("kafka.producer.serializer.value")
  private val topic = config.getString("kafka.common.topic")

  def apply(): Producer[String, String] = {
    val props = new Properties()
    props.put("bootstrap.servers", brokers)
    props.put("key.serializer", keySerializer)
    props.put("value.serializer", valueSerializer)

    new Producer[String, String](topic, props)
  }
}

class Producer[K, V](val topic: String, val props: Properties) {
  val producer = new KafkaProducer[K, V](props)

  def publish(key: K, value: V) = {
    val record = new ProducerRecord[K, V](topic, key, value)

    producer.send(record, (metadata: RecordMetadata, exception: Exception) => {
      if (exception == null) {
        println(s"Message delivered to topic ${metadata.topic()} at offset ${metadata.offset()}")
      } else {
        println(s"Error sending message: ${exception.getMessage}")
      }
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
