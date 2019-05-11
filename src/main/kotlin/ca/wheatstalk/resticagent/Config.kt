package ca.wheatstalk.resticagent

import ca.wheatstalk.resticagent.restic.ResticConfig
import org.apache.commons.configuration2.PropertiesConfiguration

fun readResticConfig(config: PropertiesConfiguration) =
    ResticConfig(
        resticPath = "restic",
        repository = config.getString("restic.repository"),
        repositoryPassword = config.getString("restic.password"),
        workingDirectory = config.getString("restic.working_directory"),
        defaultBackupPath = config.getString("restic.default_backup_path"),
        defaultRestorePath = config.getString("restic.default_restore_path"),
        awsAccessKeyId = config.getString("aws.accessKeyId"),
        awsSecretAccessKey = config.getString("aws.secretAccessKey")
    )

data class SQSConfig(
    val region: String,
    val accessKeyId: String,
    val secretKey: String,
    val queueUrl: String
) {
    val isActive get() = queueUrl.isNotEmpty()
}

fun readSqsConfig(config: PropertiesConfiguration) =
    SQSConfig(
        queueUrl = config.getString("agent.queue"),
        region = config.getString("aws.region"),
        accessKeyId = config.getString("aws.accessKeyId"),
        secretKey = config.getString("aws.secretKey")
    )