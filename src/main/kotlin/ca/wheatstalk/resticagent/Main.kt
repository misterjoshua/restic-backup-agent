package ca.wheatstalk.resticagent

import ca.wheatstalk.resticagent.restic.BeforeAfterWrappedCommandParser
import ca.wheatstalk.resticagent.restic.CommandParser
import com.amazonaws.auth.AWSStaticCredentialsProvider
import com.amazonaws.auth.BasicAWSCredentials
import com.amazonaws.services.sqs.AmazonSQS
import com.amazonaws.services.sqs.AmazonSQSClientBuilder
import org.apache.commons.configuration2.builder.fluent.Configurations

fun main(){
    try {
        val configProperties = Configurations().properties("dev.properties")!!

        val commandParser = BeforeAfterWrappedCommandParser(
            resticConfig = readResticConfig(configProperties)
        )

        commandParser.parse("restore latest").execute()

        if (false) {
            runReplLoop(commandParser)
        }

        val sqsConfig = readSqsConfig(configProperties)
        if (sqsConfig.isActive) {
            runSqsQueueLoop(sqsConfig, commandParser)
        }
    } catch (e: Exception) {
        throw e
    }
}