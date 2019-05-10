package ca.wheatstalk.resticagent.restic.commands

import ca.wheatstalk.resticagent.restic.ResticContext

class BackupCommand (resticContext: ResticContext) : CommandBase(resticContext) {
    fun backup() = backup(resticContext.defaultBackupPath)
    fun backup(backupPath: String) = runCommandBuilder.build(listOf("backup", backupPath))
}