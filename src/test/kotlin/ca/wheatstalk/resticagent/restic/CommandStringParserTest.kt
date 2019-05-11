package ca.wheatstalk.resticagent.restic

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class CommandStringParserTest {
    @Test fun commandStrings() {
        val resticPath = "/special/path/to/restic"
        val resticWorkdir = "/workdir"
        val resticDefaultBackupPath = "subdir-backup"
        val resticDefaultRestorePath = "subdir-restore"

        val resticContext = ResticConfig(
            resticPath = resticPath,
            workingDirectory = resticWorkdir,
            repository = "",
            repositoryPassword = "",
            defaultBackupPath = resticDefaultBackupPath,
            defaultRestorePath = resticDefaultRestorePath
        )

        val builder = CommandStringParser(
            resticConfig = resticContext
        )

        data class TestCase(val command: String, val expects: String)
        fun TestCase.test() =
            Assertions.assertEquals(this.expects, builder.parse(this.command).toString())

        TestCase(
            command = "backup",
            expects = "$resticPath backup $resticDefaultBackupPath")
            .test()
        TestCase(
            command = "restore latest",
            expects = "$resticPath restore --path $resticWorkdir/$resticDefaultBackupPath --target $resticDefaultRestorePath latest")
            .test()
        TestCase(
            command = "restore 1a2b3c4d",
            expects = "$resticPath restore --path $resticWorkdir/$resticDefaultBackupPath --target $resticDefaultRestorePath 1a2b3c4d")
            .test()
        TestCase(
            command = "restore --host myhostname latest",
            expects = "$resticPath restore --host myhostname --path $resticWorkdir/$resticDefaultBackupPath --target $resticDefaultRestorePath latest")
            .test()
        TestCase(
            command = "restore --host myhostname --tag mytag latest",
            expects = "$resticPath restore --host myhostname --tag mytag --path $resticWorkdir/$resticDefaultBackupPath --target $resticDefaultRestorePath latest")
            .test()
    }


}
