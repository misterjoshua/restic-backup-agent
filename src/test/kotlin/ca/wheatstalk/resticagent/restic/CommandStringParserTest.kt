package ca.wheatstalk.resticagent.restic

import ca.wheatstalk.resticagent.command.CommandEnvironmentVariables
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class CommandStringParserTest {
    @Test fun commandStrings() {
        val resticPath = "/special/path/to/restic"
        val resticWorkdir = "/workdir"
        val resticDefaultBackupPath = "subdir"

        data class TestCase(val commandString: String, val expected: String)
        val commandStrings = listOf(
            TestCase("backup","$resticPath backup $resticDefaultBackupPath"),
            TestCase("restore latest","$resticPath restore --path $resticWorkdir/$resticDefaultBackupPath --target $resticDefaultBackupPath latest"),
            TestCase("restore 1a2b3c4d","$resticPath restore --path $resticWorkdir/$resticDefaultBackupPath --target $resticDefaultBackupPath 1a2b3c4d"),
            TestCase("restore --host myhostname latest","$resticPath restore --host myhostname --path $resticWorkdir/$resticDefaultBackupPath --target $resticDefaultBackupPath latest"),
            TestCase("restore --host myhostname --tag mytag latest","$resticPath restore --host myhostname --tag mytag --path $resticWorkdir/$resticDefaultBackupPath --target $resticDefaultBackupPath latest")
        )

        val resticContext = ResticContext(
            resticPath = resticPath,
            repository = "",
            repositoryPassword = "",
            workingDirectory = resticWorkdir,
            defaultBackupPath = resticDefaultBackupPath
        )

        val builder = CommandStringParser(
            resticContext = resticContext
        )
        commandStrings.forEach {
            Assertions.assertEquals(it.expected, builder.parse(it.commandString).toString())
        }
    }


}
