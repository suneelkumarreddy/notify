#! /bin/sh

# Ensure that you have built the source!
# mvn clean install

# Delete already existing installer, if any.
rm -r notify-server-1.0.tar.gz

# Then create the installer folder.
mkdir notify-server-1.0 notify-server-1.0/logs

# Copy the required dependencies.
cp -R notify-server/target/lib notify-server-1.0/
cp -R notify-server/target/conf notify-server-1.0/
cp notify-server/target/notify-server-1.0.jar notify-server-1.0/

# Copy the configuration files.
cp notify-server/target/classes/log4j2.xml notify-server-1.0/conf/
cp notify-server/target/classes/notify.properties notify-server-1.0/conf/

# Copy the start and stop scripts.
cp start.sh notify-server-1.0/
cp stop.sh notify-server-1.0/

# Change the scripts to executable mode.
chmod a+x notify-server-1.0/start.sh
chmod a+x notify-server-1.0/stop.sh

# Tar and GZip it for release.
tar -zcvf notify-server-1.0.tar.gz notify-server-1.0

# Finish
rm -rf notify-server-1.0
