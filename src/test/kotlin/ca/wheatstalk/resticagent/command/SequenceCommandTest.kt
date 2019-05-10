package ca.wheatstalk.resticagent.command

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import java.lang.RuntimeException

internal class SequenceCommandTest {
    @Test
    fun execute() {
        class MockCommand : Command {
            var executed: Boolean = false
            override fun execute() {
                executed = true
            }
        }

        val sequenceCommand = SequenceCommand(
            arrayOf(
                MockCommand(),
                MockCommand(),
                MockCommand()
            )
        )

        assertEquals(3, sequenceCommand.sequence.size)
        sequenceCommand.sequence.forEachIndexed { index, it ->
            it as? MockCommand ?: throw RuntimeException("sequence[$index] should be a MockCommand")
            assertEquals(false, it.executed, "sequence[$index] was prematurely executed")
        }

        sequenceCommand.execute()

        sequenceCommand.sequence.forEachIndexed { index, it ->
            it as? MockCommand ?: throw RuntimeException("sequence[$index] should be a MockCommand")
            assertEquals(true, it.executed, "sequence[$index] was not executed")
        }
    }
}