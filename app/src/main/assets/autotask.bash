#!/system/bin/sh
cp /data/data/cn.ommiao.autotask/autotaskserver.jar /data/local/tmp/autotaskserver.jar
chmod 777 /data/local/tmp/autotaskserver.jar
chown shell /data/local/tmp/autotaskserver.jar
export CLASSPATH=/data/local/tmp/autotaskserver.jar
exec app_process /system cn.ommiao.server.AutoTaskServer &