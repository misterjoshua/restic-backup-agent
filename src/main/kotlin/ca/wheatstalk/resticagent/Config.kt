package ca.wheatstalk.resticagent

import ca.wheatstalk.resticagent.restic.BeforeAfterWrappedCommandParser
import ca.wheatstalk.resticagent.restic.CommandParser
import ca.wheatstalk.resticagent.restic.ResticContext
import com.amazonaws.auth.AWSStaticCredentialsProvider
import com.amazonaws.auth.BasicAWSCredentials
import com.amazonaws.services.sqs.AmazonSQS
import com.amazonaws.services.sqs.AmazonSQSClientBuilder
import org.apache.commons.configuration2.PropertiesConfiguration
import org.apache.commons.configuration2.builder.fluent.Configurations

data class Config(
    val commandQueue: String,
    val resticRepository: String,
    val resticWorkingDirectory: String,
    val resticDefaultBackupPath: String,
    val resticPassword: String,
    val awsRegion: String,
    val awsAccessKeyId: String,
    val awsSecretAccessKey: String
) {
    val basicAWSCredentials get() = BasicAWSCredentials(
            awsAccessKeyId,
            awsSecretAccessKey
        )

    val resticContext get() =
        ResticContext(
            resticPath = "restic",
            repository = resticRepository,
            repositoryPassword = resticPassword,
            workingDirectory = resticWorkingDirectory,
            defaultBackupPath = resticDefaultBackupPath,
            awsAccessKeyId = awsAccessKeyId,
            awsSecretAccessKey = awsSecretAccessKey
        )
}

fun getConfig2(config: PropertiesConfiguration) =
    Config(
        commandQueue = config.getString("agent.command_queue"),
        resticRepository = config.getString("restic.repository"),
        resticWorkingDirectory = config.getString("restic.working_directory"),
        resticDefaultBackupPath = config.getString("restic.default_backup_path"),
        resticPassword = config.getString("restic.password"),
        awsRegion = config.getString("aws.region"),
        awsAccessKeyId = config.getString("aws.accessKeyId"),
        awsSecretAccessKey = config.getString("aws.secretAccessKey")
    )

fun getConfig(): Config {
    val config = Configurations().properties("application.properties")!!
    return getConfig2(config)
}

fun getSqsClient(config: Config): AmazonSQS {
    val credential = config.basicAWSCredentials

    return with(AmazonSQSClientBuilder.standard()){
        withRegion(config.awsRegion)
        withCredentials(AWSStaticCredentialsProvider(credential))
        build()
    }
}

fun getCommandParser(config: Config): CommandParser {
    return BeforeAfterWrappedCommandParser(
        resticContext = config.resticContext
    )
}