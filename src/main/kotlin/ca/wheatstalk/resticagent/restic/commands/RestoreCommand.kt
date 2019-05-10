package ca.wheatstalk.resticagent.restic.commands

import ca.wheatstalk.resticagent.restic.ResticContext
import ca.wheatstalk.resticagent.restic.ResticRunCommandBuilder

class RestoreCommand (resticContext: ResticContext) : CommandBase(resticContext) {
    fun restore(options: String, snapshotId: String) =
        runCommandBuilder.build(buildRestoreInvocation(options, snapshotId))

    private fun buildRestoreInvocation(
        options: String,
        snapshotId: String
    ): ArrayList<String> {
        val argsList = arrayListOf("restore")

        with(options.trim()) {
            if (isNotEmpty()) {
                argsList.addAll(split(" "))
            }
        }

        argsList.addAll(
            listOf(
                "--path", "${resticContext.workingDirectory}/${resticContext.defaultBackupPath}",
                "--target", resticContext.defaultBackupPath,
                snapshotId
            )
        )
        return argsList
    }
}