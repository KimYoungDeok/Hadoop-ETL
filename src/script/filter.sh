#!/bin/sh

UCLOUD="root@14.63.225.83"
INPUT="youngdeok/etl_data/"
OUTPUT="youngdeok/etl_filter/output"
MAPREDUCE="FrontDriver"
DELIMITER=","
COMMAND="-filter GT::1::20,EQ::3::직장인"

ssh $UCLOUD hadoop fs -rmr $OUTPUT
ssh $UCLOUD hadoop jar /root/youngdeok/hadoop-example.jar org.openflamingo.hadoop.mapreduce.$MAPREDUCE -input $INPUT -output $OUTPUT -delimiter $DELIMITER $COMMAND
ssh $UCLOUD hadoop fs -cat $OUTPUT/part-*


