package ca.wheatstalk.resticagent.command

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class NoOpCommandTest {
    @Test
    fun testExecute() {
        assertDoesNotThrow {
            val command = NoOpCommand("Do nothing")
            command.execute()
        }
    }
}