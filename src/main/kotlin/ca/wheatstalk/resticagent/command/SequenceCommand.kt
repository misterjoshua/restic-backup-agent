package ca.wheatstalk.resticagent.command

import org.slf4j.LoggerFactory

class SequenceCommand(
    val sequence: Array<Command>
): Command {
    private val logger = LoggerFactory.getLogger(javaClass)

    override fun execute() {
        logger.debug("Sequence of ${sequence.size} commands")
        sequence.forEachIndexed { index, it ->
            logger.debug("Executing command[$index]")
            it.execute()
        }
    }
}