package model

object Message {
  def apply(key: String, value: String): Message[String, String] = {
    new Message[String, String](key, value)
  }
}

case class Message[K, V](key: K, value: V) {}
