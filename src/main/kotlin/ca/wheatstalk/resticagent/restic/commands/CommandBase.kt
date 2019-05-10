package ca.wheatstalk.resticagent.restic.commands

import ca.wheatstalk.resticagent.restic.ResticContext
import ca.wheatstalk.resticagent.restic.ResticRunCommandBuilder

open class CommandBase(protected val resticContext: ResticContext) {
    protected val runCommandBuilder = ResticRunCommandBuilder(resticContext = resticContext)
}