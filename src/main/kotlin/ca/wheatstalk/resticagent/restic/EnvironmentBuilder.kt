package ca.wheatstalk.resticagent.restic

import ca.wheatstalk.resticagent.command.CommandEnvironmentVariables

class EnvironmentBuilder (
    private val resticConfig: ResticConfig
) {
    fun build(): CommandEnvironmentVariables =
        mapOf(
            "RESTIC_REPOSITORY" to resticConfig.repository,
            "RESTIC_PASSWORD" to resticConfig.repositoryPassword,
            "AWS_ACCESS_KEY_ID" to resticConfig.awsAccessKeyId,
            "AWS_SECRET_ACCESS_KEY" to resticConfig.awsSecretAccessKey
        ).filter { it.value.isNotEmpty() }
}