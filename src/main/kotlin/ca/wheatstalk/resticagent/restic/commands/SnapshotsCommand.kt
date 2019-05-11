package ca.wheatstalk.resticagent.restic.commands

import ca.wheatstalk.resticagent.restic.ResticConfig

class SnapshotsCommand (resticConfig: ResticConfig) : CommandBase(resticConfig) {
    fun snapshots() = runCommandBuilder.build(listOf("snapshots"))
}