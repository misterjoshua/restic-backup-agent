package ca.wheatstalk.resticagent

import ca.wheatstalk.resticagent.restic.CommandParser

fun loadConfig(file: String) {

}

fun main(){
    try {
        val config = getConfig()
        val commandParser = getCommandParser(config)

//        runJLineRepl(commandBuilder)
//        commandBuilder.parse("restore latest").execute()
        runReplLoop(commandParser)
//        runQueueLoop(config, commandParser)
    } catch (e: Exception) {
        throw e
    }
}

fun runReplLoop(commandParser: CommandParser) {
    loop@ while (true) {
        print("restic> ")
        val line = readLine()

        when (line) {
            null -> break@loop
            "exit" -> break@loop
            else -> {
                try {
                    commandParser.parse(line).execute()
                } catch (e: Exception) {
                    println("Error: $e")
                    continue@loop
                }
            }
        }
    }
}

fun runQueueLoop(
    config: Config,
    commandParser: CommandParser
) {
    val sqs = getSqsClient(config)
    while (true) {
        val messageResult = sqs.receiveMessage(config.commandQueue)
        for (message in messageResult.messages) {
            try {
                commandParser.parse(message.body).execute()
            } catch (e: Matcher.UnknownPatternException) {
                println(e)
            } catch (e: Exception) {
                throw e
            }
            finally {
                sqs.deleteMessage(config.commandQueue, message.receiptHandle)
            }
        }
    }
}


