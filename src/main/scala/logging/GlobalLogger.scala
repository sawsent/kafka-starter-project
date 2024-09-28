package logging

import org.apache.logging.log4j.{LogManager, Logger}

object GlobalLogger {
  val logger: Logger = LogManager.getLogger("GlobalLogger")
}
