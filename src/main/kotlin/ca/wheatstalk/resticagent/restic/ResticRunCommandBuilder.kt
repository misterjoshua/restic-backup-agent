package ca.wheatstalk.resticagent.restic

import ca.wheatstalk.resticagent.command.CommandInvocation
import ca.wheatstalk.resticagent.command.RunCommand

class ResticRunCommandBuilder(
    val resticContext: ResticContext
) {
    private val environmentBuilder = EnvironmentBuilder(resticContext = resticContext)

    fun build(invocation: CommandInvocation) =
        RunCommand(
            invocation = listOf(resticContext.resticPath) + invocation,
            environment = environmentBuilder.build(),
            workDirectory = resticContext.workingDirectory
        )
}