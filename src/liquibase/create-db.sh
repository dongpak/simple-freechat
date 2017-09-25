#!/bin/sh
echo "Enter the master password ..."
mysql -u root -p <<EOF
create database freechat;
create user 'freechat' identified by '$1';
grant all on freechat.* to freechat;
flush privileges;
show grants for freechat;
EOF