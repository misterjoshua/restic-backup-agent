package ca.wheatstalk.resticagent.restic

import ca.wheatstalk.resticagent.command.NoOpCommand
import ca.wheatstalk.resticagent.command.SequenceCommand
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

internal class WrappedCommandTest {
    @Test
    fun myTest() {
        val context = ResticConfig()
        val parser = CommandStringParser(resticConfig = context)

        val backupCommand = parser.parse("backup") as SequenceCommand
        assertTrue(backupCommand.sequence[0] is NoOpCommand)
        assertTrue(backupCommand.sequence[1].toString().contains("restic backup"))
        assertTrue(backupCommand.sequence[2] is NoOpCommand)

        val restorecommand = parser.parse("restore latest") as SequenceCommand
        assertTrue(restorecommand.sequence[0] is NoOpCommand)
        assertTrue(restorecommand.sequence[1].toString().contains("restic restore"))
        assertTrue(restorecommand.sequence[2] is NoOpCommand)

        assertFalse(parser.parse("snapshots") is SequenceCommand)
    }
}