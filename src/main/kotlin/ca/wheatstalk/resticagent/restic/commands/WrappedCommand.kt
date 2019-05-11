package ca.wheatstalk.resticagent.restic.commands

import ca.wheatstalk.resticagent.command.Command
import ca.wheatstalk.resticagent.command.NoOpCommand
import ca.wheatstalk.resticagent.command.SequenceCommand
import ca.wheatstalk.resticagent.restic.ResticConfig

class WrappedCommand (resticConfig: ResticConfig) : CommandBase(resticConfig) {
    fun wrap(command: Command) =
        object : SequenceCommand(
            arrayOf(
                createRunBeforeCommandWithCommandString(),
                command,
                createRunAfterCommandWithCommandString()
            )
        ) {
            override fun toString() = sequence[1].toString()
        }

    private fun createRunAfterCommandWithCommandString() = NoOpCommand("Placeholder for after-operation command")
    private fun createRunBeforeCommandWithCommandString() = NoOpCommand("Placeholder for before-operation command")
}