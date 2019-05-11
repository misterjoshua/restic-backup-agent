package ca.wheatstalk.resticagent.restic.commands

import ca.wheatstalk.resticagent.restic.ResticConfig

class InitCommand (resticConfig: ResticConfig) : CommandBase(resticConfig) {
    fun buildInit() = runCommandBuilder.build(listOf("init"))
}