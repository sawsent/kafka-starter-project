package consumer

import consumer.MessageConsumer.POLL_TIMEOUT_DURATION
import logging.GlobalLogging
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

class MessageConsumer(val topic: String, val props: Properties) extends GlobalLogging {

  val consumer = new KafkaConsumer[String, String](props)
  consumer.subscribe(Collections.singletonList(topic))

  private val processMessage = (msg: Message[String, String]) => {
    logger.info(s"Processing $msg")

    consumer.commitSync()
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
