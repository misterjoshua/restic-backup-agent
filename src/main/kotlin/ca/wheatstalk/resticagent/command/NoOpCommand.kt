package ca.wheatstalk.resticagent.command

import org.slf4j.LoggerFactory

class NoOpCommand(
    private val description: String = "No operation performed"
): Command {
    private val logger = LoggerFactory.getLogger(javaClass)

    override fun execute() {
        logger.debug(description)
    }
}