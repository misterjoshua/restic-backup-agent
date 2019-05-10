package ca.wheatstalk.resticagent.command

import org.slf4j.LoggerFactory
import java.io.File
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

            start()
        }

        val exitValue = process.waitFor()
        logger.info("Process finished with exit value $exitValue")

        if (exitValue != 0) {
            throw RuntimeException("The command failed. See output.")
        }
    }

    override fun toString() = invocation.joinToString(" ")
}