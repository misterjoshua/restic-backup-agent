package ca.wheatstalk.resticagent.restic

data class ResticContext(
    val resticPath: String = "restic",
    val workingDirectory: String = "/",

    val repository: String = "",
    val repositoryPassword: String = "",
    val defaultBackupPath: String = ".",

    val awsAccessKeyId: String = "",
    val awsSecretAccessKey: String = ""
)