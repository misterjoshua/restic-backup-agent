package ca.wheatstalk.resticagent.restic

import ca.wheatstalk.resticagent.command.*
import org.slf4j.LoggerFactory

class BeforeAfterWrappedCommandParser(
    resticContext: ResticContext
): CommandParser {
    private val logger = LoggerFactory.getLogger(javaClass)
    private val commandStringParser = CommandStringParser(resticContext = resticContext)

    override fun parse(commandString: String): Command {
        return SequenceCommand(
            arrayOf(
                createRunBeforeCommandWithCommandString(commandString),
                commandStringParser.parse(commandString),
                createRunAfterCommandWithCommandString(commandString)
            )
        )
    }

    private fun createRunAfterCommandWithCommandString(commandString: String) = NoOpCommand("Placeholder for after-operation command with $commandString")
    private fun createRunBeforeCommandWithCommandString(commandString: String) = NoOpCommand("Placeholder for before-operation command with $commandString")
}