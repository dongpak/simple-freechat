#!/bin/sh
cd changelog
CP="../liquibase.jar:../snakeyaml-1.17.jar:../mysql-connector-java-5.0.8-bin.jar"
java -cp $CP liquibase.integration.commandline.Main --driver=com.mysql.jdbc.Driver --changeLogFile=db.changelog-master.xml --url="jdbc:mysql://localhost/freechat" --username=freechat --password=$1 migrate
