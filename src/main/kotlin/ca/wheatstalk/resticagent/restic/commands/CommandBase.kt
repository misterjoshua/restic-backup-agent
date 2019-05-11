package ca.wheatstalk.resticagent.restic.commands

import ca.wheatstalk.resticagent.restic.ResticConfig
import ca.wheatstalk.resticagent.restic.ResticRunCommandBuilder

open class CommandBase(protected val resticConfig: ResticConfig) {
    protected val runCommandBuilder = ResticRunCommandBuilder(resticConfig = resticConfig)
}