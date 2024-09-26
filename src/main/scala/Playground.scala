object Playground extends App {

  val producer = Producer()
  val consumer = Consumer()

  producer.publish("key", "Hello there mate!")

  val consumerThread: Thread = consumer.listen()

  (1 to 100).foreach(x => producer.publish("mes", s"Hi, this is my message $x"))

}