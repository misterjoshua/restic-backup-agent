package ca.wheatstalk.resticagent.restic.commands

import ca.wheatstalk.resticagent.restic.ResticConfig

class RestoreCommand (resticConfig: ResticConfig) : CommandBase(resticConfig) {
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
                "--path", "${resticConfig.workingDirectory}/${resticConfig.defaultBackupPath}",
                "--target", resticConfig.defaultRestorePath,
                snapshotId
            )
        )
        return argsList
    }
}