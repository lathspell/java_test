#!/bin/bash
#
# Start a Java SE class as daemon.
#
# Signals (at least TERM) are catched to enable a graceful shutdown.
#
# Run this wrapper with "nohup ./MyApp >/dev/null 2>&1 &" or, if the
# automatic restart is not desired, just with jsvc but without the
# -nodetach parameter.
#

while true; do
    /usr/bin/jsvc \
      -outfile SYSLOG \
      -errfile SYSLOG \
      -pidfile /tmp/MyApp.pid \
      -nodetach \
      -java-home /usr/lib/jvm/java-7-openjdk-amd64/ \
      -cp `pwd`/target/java_test_jsvc-1.0-SNAPSHOT-jar-with-dependencies.jar \
      de.lathspell.java_test_jsvc.MyApp 

    logger -t "$0" "MyApp exited!"
    sleep 2
done
