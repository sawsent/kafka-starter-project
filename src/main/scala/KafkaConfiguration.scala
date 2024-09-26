import com.typesafe.config.ConfigFactory
import org.apache.kafka.clients.consumer.ConsumerConfig

import java.util.Properties

object KafkaConfiguration {
  private val config = ConfigFactory.load()

  private lazy val topic = config.getString("kafka.common.topic")
  private lazy val brokers = config.getString("kafka.common.brokers")

  def consumerProperties(): Properties = {
    val keyDeserializer = config.getString("kafka.consumer.deserializer.key")
    val valueDeserializer = config.getString("kafka.consumer.deserializer.value")
    val groupId = config.getString("kafka.consumer.groupId")

    val props = new Properties()
    props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, brokers)
    props.put(ConsumerConfig.GROUP_ID_CONFIG, groupId)
    props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, keyDeserializer)
    props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, valueDeserializer)
    props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest")

    props
  }

  def producerProperties(): Properties = {
    val keySerializer = config.getString("kafka.producer.serializer.key")
    val valueSerializer = config.getString("kafka.producer.serializer.value")

    val props = new Properties()
    props.put("bootstrap.servers", brokers)
    props.put("key.serializer", keySerializer)
    props.put("value.serializer", valueSerializer)

    props
  }

  def kafkaTopic(): String = topic


}
