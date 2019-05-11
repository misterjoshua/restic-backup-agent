package ca.wheatstalk.resticagent

import ca.wheatstalk.resticagent.restic.CommandStringParser
import org.apache.commons.configuration2.builder.fluent.Configurations

fun main(){
    try {
        val configProperties = Configurations().properties("dev.properties")!!

        val commandParser = CommandStringParser(
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