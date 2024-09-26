import com.typesafe.config.{Config, ConfigFactory}
import org.apache.kafka.clients.consumer.{ConsumerConfig, KafkaConsumer}

import java.time.Duration
import java.util.{Collections, Properties}

object Consumer {
  private val config = ConfigFactory.load()

  private val topic = config.getString("kafka.common.topic")
  private val brokers = config.getString("kafka.common.brokers")
  private val keyDeserializer = config.getString("kafka.consumer.deserializer.key")
  private val valueDeserializer = config.getString("kafka.consumer.deserializer.value")
  private val groupId = config.getString("kafka.consumer.groupId")

  def apply(): Consumer[String, String] = {

    val props = new Properties()
    props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, brokers)
    props.put(ConsumerConfig.GROUP_ID_CONFIG, groupId)
    props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, keyDeserializer)
    props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, valueDeserializer)
    props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest")

    new Consumer[String, String](topic, brokers, props)
  }
}

class Consumer[K, V](val topic: String, val brokers: String, val props: Properties) {

  val consumer = new KafkaConsumer[K, V](props)
  consumer.subscribe(Collections.singletonList(topic))

  def listen(): Thread = {
    val thread = new Thread(() => while (true) {
      val records = consumer.poll(Duration.ofMillis(3000))
      records.forEach(rec => {
        val key = rec.key()
        val value = rec.value()
        println(s"[Consumer] received new message with key: '$key', and value: '$value''")
      })
      consumer.commitSync(Duration.ofMillis(1000))
    })
    thread.start()
    thread
  }

  def listen(callback: (K, V) => Unit): Thread = {
    val thread = new Thread(() => while (true) {
      val records = consumer.poll(Duration.ofMillis(3000))
      records.forEach(rec => {
        val key = rec.key()
        val value = rec.value()
        callback(key, value)
      })
      consumer.commitSync(Duration.ofMillis(1000))
    })
    thread.start()
    thread
  }
}
