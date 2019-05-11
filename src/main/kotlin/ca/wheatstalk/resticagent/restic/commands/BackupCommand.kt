package ca.wheatstalk.resticagent.restic.commands

import ca.wheatstalk.resticagent.restic.ResticConfig

class BackupCommand (resticConfig: ResticConfig) : CommandBase(resticConfig) {
    fun backup() = backup(resticConfig.defaultBackupPath)
    fun backup(backupPath: String) = runCommandBuilder.build(listOf("backup", backupPath))
}