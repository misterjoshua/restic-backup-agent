package ca.wheatstalk.resticagent.command

import org.slf4j.LoggerFactory
import java.io.File
import java.io.IOException
import java.lang.Exception
import java.lang.RuntimeException

class RunCommand(
    private val invocation: CommandInvocation,
    private val environment: CommandEnvironmentVariables,
    private val workDirectory: String,
    private val processBuilder: ProcessBuilder = ProcessBuilder()
): Command {
    private val logger = LoggerFactory.getLogger(javaClass)

    override fun execute() {
        logger.info("Executing command in $workDirectory: $invocation")

        val process = with (processBuilder) {
            command(invocation)
            directory(File(workDirectory))

            redirectOutput(ProcessBuilder.Redirect.INHERIT)
            redirectError(ProcessBuilder.Redirect.INHERIT)

            environment.forEach {
                environment()[it.key] = it.value
            }

            try {
                start()
            } catch (e: IOException) {
                throw CommandProbablyDoesntExistException("IO Exception running $invocation. Check that the command exists. $e: ${e.message}")
            }
        }

        val exitValue = process.waitFor()
        logger.info("Process finished with exit value $exitValue")

        if (exitValue != 0) {
            throw RuntimeException("The command failed. See output.")
        }
    }

    override fun toString() = invocation.joinToString(" ")

    class CommandProbablyDoesntExistException(message: String): Exception(message)
}