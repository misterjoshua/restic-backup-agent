package ca.wheatstalk.resticagent.restic

import ca.wheatstalk.resticagent.command.CommandEnvironmentVariables

class EnvironmentBuilder (
    private val resticContext: ResticContext
) {
    fun build(): CommandEnvironmentVariables =
        mapOf(
            "RESTIC_REPOSITORY" to resticContext.repository,
            "RESTIC_PASSWORD" to resticContext.repositoryPassword,
            "AWS_ACCESS_KEY_ID" to resticContext.awsAccessKeyId,
            "AWS_SECRET_ACCESS_KEY" to resticContext.awsSecretAccessKey
        ).filter { it.value.isNotEmpty() }
}