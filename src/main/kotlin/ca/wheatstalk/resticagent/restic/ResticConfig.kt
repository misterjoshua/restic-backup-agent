package ca.wheatstalk.resticagent.restic

data class ResticConfig(
    val resticPath: String = "restic",
    val workingDirectory: String = "/",

    val repository: String = "",
    val repositoryPassword: String = "",
    val defaultBackupPath: String = ".",
    val defaultRestorePath: String = "",

    val awsAccessKeyId: String = "",
    val awsSecretAccessKey: String = ""
)