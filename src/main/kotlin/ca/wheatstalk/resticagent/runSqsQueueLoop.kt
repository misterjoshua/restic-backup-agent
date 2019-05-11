package ca.wheatstalk.resticagent

import ca.wheatstalk.resticagent.restic.CommandParser
import com.amazonaws.auth.AWSStaticCredentialsProvider
import com.amazonaws.auth.BasicAWSCredentials
import com.amazonaws.services.sqs.AmazonSQS
import com.amazonaws.services.sqs.AmazonSQSClientBuilder

fun runSqsQueueLoop(
    config: SQSConfig,
    commandParser: CommandParser
) {
    val sqs = amazonSQS(config)

    while (true) {
        val messageResult = sqs.receiveMessage(config.queueUrl)
        for (message in messageResult.messages) {
            try {
                commandParser.parse(message.body).execute()
            } catch (e: Matcher.UnknownPatternException) {
                println(e)
            } catch (e: Exception) {
                throw e
            }
            finally {
                sqs.deleteMessage(config.queueUrl, message.receiptHandle)
            }
        }
    }
}

private fun amazonSQS(config: SQSConfig): AmazonSQS {
    val credential = BasicAWSCredentials(
        config.accessKeyId,
        config.secretKey
    )

    return with(AmazonSQSClientBuilder.standard()) {
        withRegion(config.region)
        withCredentials(AWSStaticCredentialsProvider(credential))
        build()
    }!!
}