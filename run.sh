#! /bin/sh

java -jar notify-server-1.0.jar 5 &
echo $! > notify.pid
