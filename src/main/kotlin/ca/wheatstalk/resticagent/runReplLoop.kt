package ca.wheatstalk.resticagent

import ca.wheatstalk.resticagent.restic.CommandParser

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