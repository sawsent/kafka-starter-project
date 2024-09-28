package app

import config.KafkaConfig
import model.Message
import producer.MessageProducer

import scala.io.StdIn

object App extends App {

  private val producer = MessageProducer(KafkaConfig.TOPIC, KafkaConfig.producerProperties)

  while (true) {
    print("\nEnter message key: ")
    val key = StdIn.readLine()
    print("Enter message value: ")
    val value = StdIn.readLine()
    val msg = Message(key, value)
    print(s"You entered message $msg. Ready to send? (y/n) ")
    val res = StdIn.readLine()
    if ("Yy".contains(res)) {
      println(s"Publishing message $msg")
      producer.publish(msg)
    }
  }

}
