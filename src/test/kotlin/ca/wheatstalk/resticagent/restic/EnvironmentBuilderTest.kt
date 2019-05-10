package ca.wheatstalk.resticagent.restic

import ca.wheatstalk.resticagent.command.CommandEnvironmentVariables
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class EnvironmentBuilderTest {
    @Test fun commandEnvironment() {
        val resticPassword = "password"
        val resticRepository = "myrepository"
        val resticAwsAccessKeyId = "accesskeyid"
        val resticAwsSecretAccessKey = "secretkey"

        val resticContext = ResticContext(
            repository = resticRepository,
            repositoryPassword = resticPassword,
            awsAccessKeyId = resticAwsAccessKeyId,
            awsSecretAccessKey = resticAwsSecretAccessKey,
            defaultBackupPath = ""
        )

        val envBuilder = EnvironmentBuilder(resticContext)

        data class CommandEnvironmentVariable(val name: String, val value: String)
        val res = envBuilder.build()
        fun CommandEnvironmentVariables.assertContains(element: CommandEnvironmentVariable, message: String) {
            assertTrue(this.containsKey(element.name), message)
            assertEquals(element.value, this[element.name])
        }

        res.assertContains(CommandEnvironmentVariable("RESTIC_PASSWORD", resticPassword), "Restic password missing")
        res.assertContains(CommandEnvironmentVariable("RESTIC_REPOSITORY", resticRepository), "Restic repository missing")
        res.assertContains(CommandEnvironmentVariable("AWS_ACCESS_KEY_ID", resticAwsAccessKeyId), "AWS access key missing")
        res.assertContains(CommandEnvironmentVariable("AWS_SECRET_ACCESS_KEY", resticAwsSecretAccessKey), "AWS secret access key missing")
    }
}