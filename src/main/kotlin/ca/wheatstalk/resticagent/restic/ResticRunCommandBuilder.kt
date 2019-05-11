package ca.wheatstalk.resticagent.restic

import ca.wheatstalk.resticagent.command.CommandInvocation
import ca.wheatstalk.resticagent.command.RunCommand

class ResticRunCommandBuilder(
    val resticConfig: ResticConfig
) {
    private val environmentBuilder = EnvironmentBuilder(resticConfig = resticConfig)

    fun build(invocation: CommandInvocation) =
        RunCommand(
            invocation = listOf(resticConfig.resticPath) + invocation,
            environment = environmentBuilder.build(),
            workDirectory = resticConfig.workingDirectory
        )
}