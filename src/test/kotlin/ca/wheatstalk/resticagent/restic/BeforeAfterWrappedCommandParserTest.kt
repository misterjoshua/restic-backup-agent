package ca.wheatstalk.resticagent.restic

import ca.wheatstalk.resticagent.command.NoOpCommand
import ca.wheatstalk.resticagent.command.SequenceCommand
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

internal class BeforeAfterWrappedCommandParserTest {
    @Test
    fun myTest() {
        val context = ResticContext()
        val parser = BeforeAfterWrappedCommandParser(resticContext = context)

        val sequence = parser.parse("snapshots") as? SequenceCommand ?: throw Assertions.fail("It didn't return a sequence of commands")

        Assertions.assertTrue(sequence.sequence[0] is NoOpCommand)
        Assertions.assertTrue(sequence.sequence[1].toString().contains("restic snapshots"))
        Assertions.assertTrue(sequence.sequence[2] is NoOpCommand)
    }
}