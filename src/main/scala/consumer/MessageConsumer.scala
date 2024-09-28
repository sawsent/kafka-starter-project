package consumer

import consumer.MessageConsumer.POLL_TIMEOUT_DURATION
import model.Message
import org.apache.kafka.clients.consumer.KafkaConsumer

import java.time.Duration
import java.util.{Collections, Properties}

object MessageConsumer {
  private val POLL_TIMEOUT_DURATION: Duration = Duration.ofMillis(100)

  def apply(topic: String, props: Properties): MessageConsumer = {
    new MessageConsumer(topic, props)
  }
}

class MessageConsumer(val topic: String, val props: Properties) {

  val consumer = new KafkaConsumer[String, String](props)
  consumer.subscribe(Collections.singletonList(topic))

  private val processMessage = (msg: Message[String, String]) => {
    // Implement Logger
    println(s"[Consumer] Processing message $msg")
  }

  def consumeMessages(): Unit = {
    while (true) {
      val records = consumer.poll(POLL_TIMEOUT_DURATION)
      records.forEach(rec => {
        val msg: Message[String, String] = Message(rec.key(), rec.value())
        processMessage(msg)
      })
    }
  }


}
