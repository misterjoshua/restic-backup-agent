package ca.wheatstalk.resticagent.restic

import ca.wheatstalk.resticagent.restic.commands.InitCommand
import org.apache.commons.io.FileUtils
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.io.File
import java.nio.file.Paths

internal class FullTest {
    @Test
    fun myTest() {
        val testDir = Paths.get(System.getProperty("java.io.tmpdir"), "restic").toAbsolutePath().toString()

        FileUtils.deleteDirectory(File(testDir))

        val contentDir = "$testDir/content"
        File(contentDir).mkdirs()

        val config = ResticConfig(
            workingDirectory = testDir,
            repository = "repo",
            repositoryPassword = "password",
            defaultBackupPath = "content",
            defaultRestorePath = "."
        )

        // Check that init worked.
        InitCommand(config).buildInit().execute()
        assertTrue(File("$testDir/repo").exists())
        assertTrue(File("$testDir/repo/config").exists())
        assertTrue(File("$testDir/repo/data").exists())
        assertTrue(File("$testDir/repo/index").exists())

        fun contentPath(path: String) = "$contentDir/$path"
        fun testFile(path: String) = File(contentPath(path)).isFile
        fun makeFile(path: String) {
            File(contentPath(path)).writeText("test content")
            assertTrue(testFile(path))
        }

        val commandStringParser = CommandStringParser(config)

        // Make a file and take a backup
        makeFile("test1")
        commandStringParser.parse("backup").execute()

        // Delete everything
        FileUtils.deleteDirectory(File(contentDir))
        assertFalse(testFile("test1"))

        // Restore and add a file
        commandStringParser.parse("restore latest").execute()
        assertTrue(testFile("test1"))
        makeFile("test2")

        // Backup & delete everything
        commandStringParser.parse("backup").execute()
        FileUtils.deleteDirectory(File(contentDir))

        // Restore and look for both files.
        commandStringParser.parse("restore latest").execute()
        assertTrue(testFile("test1"))
        assertTrue(testFile("test2"))
    }
}