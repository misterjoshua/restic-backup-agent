package ca.wheatstalk.resticagent.restic

import ca.wheatstalk.resticagent.command.Command

interface CommandParser {
    fun parse(commandString: String): Command
}