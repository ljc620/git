#!/bin/bash

PRG="$0"
PRGDIR=`dirname "$PRG"`

kill -9 `cat "$PRGDIR"/tbims.pid` 


