package app

import config.KafkaConfig
import logging.GlobalLogger.logger
import logging.LoggerFactory
import model.Message
import org.apache.logging.log4j.{LogManager, Logger}
import producer.MessageProducer

import scala.io.StdIn


object App extends App {
  private val logger = LoggerFactory("App")

  private val producer = MessageProducer(KafkaConfig.TOPIC, KafkaConfig.producerProperties)

  logger.info("Starting App.")

  while (true) {
    print("\nEnter message key: ")
    val key = StdIn.readLine()
    print("Enter message value: ")
    val value = StdIn.readLine()
    val msg = Message(key, value)
    print(s"You entered $msg. Ready to send? (y/n) ")
    val res = StdIn.readLine()
    if ("Yy".contains(res)) producer.publish(msg) else println(s"Cancelling... $msg not sent.")
  }

}
