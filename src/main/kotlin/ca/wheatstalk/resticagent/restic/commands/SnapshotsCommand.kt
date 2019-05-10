package ca.wheatstalk.resticagent.restic.commands

import ca.wheatstalk.resticagent.restic.ResticContext
import ca.wheatstalk.resticagent.restic.ResticRunCommandBuilder

class SnapshotsCommand (resticContext: ResticContext) : CommandBase(resticContext) {
    fun snapshots() = runCommandBuilder.build(listOf("snapshots"))
}