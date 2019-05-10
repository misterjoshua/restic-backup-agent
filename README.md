<img src="https://img.shields.io/docker/automated/wheatstalk/restic-backup-agent.svg" /> <img src="https://img.shields.io/docker/build/wheatstalk/restic-backup-agent.svg" />

# restic-backup-agent
The Restic Backup Agent provides automation for backup and restore using the excellent restic backup software. The agent software allows you do the following:

## Features
* Tell Restic what to backup and where to restore
* Invoke pre- and post-backup scripts to dump databases, lock and unlock tables, validate, or otherwise
* Perform backups on a schedule
* Perform restoration on a schedule
* Restore the latest backup when a directory (or container volume) is empty
* Monitor a queue for backup and restore instructions

## Supported Environments
* Run as a sidecar Docker container
* Run as standalone software on a server
* Configure the agent through environment variables
* Configure the agent through file-based configuration
