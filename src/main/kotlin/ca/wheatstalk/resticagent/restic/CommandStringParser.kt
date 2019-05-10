package ca.wheatstalk.resticagent.restic

import ca.wheatstalk.resticagent.command.Command
import ca.wheatstalk.resticagent.firstMatch
import ca.wheatstalk.resticagent.restic.commands.BackupCommand
import ca.wheatstalk.resticagent.restic.commands.RestoreCommand
import ca.wheatstalk.resticagent.restic.commands.SnapshotsCommand
import org.slf4j.LoggerFactory

data class CommandStringParser(
    private val resticContext: ResticContext
): CommandParser {
    private val logger = LoggerFactory.getLogger(javaClass)
    private val runCommandBuilder = ResticRunCommandBuilder(resticContext = resticContext)

    override fun parse(commandString: String) =
        firstMatch<Command>(commandString) {
            // Asking for a backup
            matchPattern("^backup$") {
                logger.debug("Parsing backup command")
                BackupCommand(resticContext).backup()
            }
            // Asking for a snapshot list
            matchPattern("^snapshots$") {
                logger.debug("Parsing snapshot command")
                SnapshotsCommand(resticContext).snapshots()
            }
            // Asking for a restore, with options
            matchPattern("^restore(( --(exclude [^ ]+|host [^ ]+|include [^ ]+|tag [^ ]+|verify))*) ([a-z0-9]+)$") {
                val (options, _, _, snapshotId) = it.destructured

                logger.debug("Parsing restore command for $snapshotId with options $options")
                RestoreCommand(resticContext).restore(options, snapshotId)
            }
        }
}

