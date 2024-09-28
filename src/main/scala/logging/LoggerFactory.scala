package logging

import org.apache.logging.log4j.{LogManager, Logger}

object LoggerFactory {
  def apply(name: String): Logger = LogManager.getLogger(name)
}
