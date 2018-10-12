#!/bin/bash

PRG="$0"
PRGDIR=`dirname "$PRG"`

nohup java -Xms1024m -Xmx15000m -XX:PermSize=512m -XX:MaxNewSize=512m -XX:MaxPermSize=512m -jar $PRGDIR/tbims.server.rpc-20170629.jar >> $PRGDIR/tbims.log 2<&1 &
echo $! > $PRGDIR/tbims.pid

