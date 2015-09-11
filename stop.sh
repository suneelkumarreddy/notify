#!/bin/bash

pid=`cat notify.pid`

if [ ! -e /proc/$pid -a /proc/$pid/exe ]; then
	echo "[ERROR] Server is not running. PID $pid not found."
	echo "[ERROR] Try running 'ps ax | grep notify' and verify."
else
    kill $pid
fi
