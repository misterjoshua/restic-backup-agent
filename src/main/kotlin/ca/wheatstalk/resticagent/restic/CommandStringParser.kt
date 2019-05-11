package ca.wheatstalk.resticagent.restic

import ca.wheatstalk.resticagent.command.Command
import ca.wheatstalk.resticagent.firstMatch
import ca.wheatstalk.resticagent.restic.commands.BackupCommand
import ca.wheatstalk.resticagent.restic.commands.RestoreCommand
import ca.wheatstalk.resticagent.restic.commands.SnapshotsCommand
import org.slf4j.LoggerFactory

data class CommandStringParser(
    private val resticConfig: ResticConfig
): CommandParser {
    private val logger = LoggerFactory.getLogger(javaClass)
    private val runCommandBuilder = ResticRunCommandBuilder(resticConfig = resticConfig)

    override fun parse(commandString: String) =
        firstMatch<Command>(commandString) {
            // Asking for a backup
            matchPattern("^backup$") {
                logger.debug("Parsing backup command")
                BackupCommand(resticConfig).backup()
            }
            // Asking for a snapshot list
            matchPattern("^snapshots$") {
                logger.debug("Parsing snapshot command")
                SnapshotsCommand(resticConfig).snapshots()
            }
            // Asking for a restore, with options
            matchPattern("^restore(( --(exclude [^ ]+|host [^ ]+|include [^ ]+|tag [^ ]+|verify))*) ([a-z0-9]+)$") {
                val (options, _, _, snapshotId) = it.destructured

                logger.debug("Parsing restore command for $snapshotId with options $options")
                RestoreCommand(resticConfig).restore(options, snapshotId)
            }
        }
}

