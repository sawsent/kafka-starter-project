package logging

import org.apache.logging.log4j.{LogManager, Logger}

trait GlobalLogging {
  protected val logger: Logger = LogManager.getLogger(getClass.getName)
}
