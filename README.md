<img src="https://img.shields.io/docker/cloud/automated/wheatstalk/restic-backup-agent.svg" /> <img src="https://img.shields.io/docker/cloud/build/wheatstalk/restic-backup-agent.svg" />

# restic-backup-agent
The Restic Backup Agent provides automation for backup and restore using the excellent restic backup software.

## Features
The agent tells Restic what to backup and where to restore, but also does all of the following:

* Performs backups on a schedule
* Performs restorations on a schedule (i.e., backup validation)
* Invokes user-supplied pre- and post-backup scripts (dump databases, lock and unlock tables, or otherwise produce application consistent backups)
* Invokes user-supplied pre- and post-restoration scripts (validate, roll back)
* Conditionally restores the latest backup when starting up (i.e., when a container volume isn't yet populated with application data)
* Monitors a queue for backup and restore instructions
* Accepts backup and restore instructions from a rest API
* Accepts configuration through environment variables
* Accepts configuration through a configuration file

## Supported Environments
* Run as a sidecar Docker container
* Run as standalone software on a server
