#! /bin/bash
#on verifie le nombre d'argument
if [ $# == 3 ]; 
then

# Get the input data
declare INPUT=$2;
declare OUTPUT=$3;
declare CONFIG=$1;

export HADOOP_HOME=/usr/local/hadoop
#on verifie si le fichier output n'existe pas deja sinon on le supprime

if [ -d $3 ];
then
${HADOOP_HOME}/bin/hadoop fs -rmr $OUTPUT >& /dev/null
fi

# Execute the jobs
printf "\nExecuting Job 1: create iverted index\n"
${HADOOP_HOME}/bin/hadoop jar moteurIndexation.jar index $CONFIG $INPUT $OUTPUT

else
printf "./indexation.sh [conf] <input_dir> <output_dir> \n"
printf " input_dir  = Directory with the input files\n"
printf " output_dir = Directory to place the output\n"
printf " conf       = Optional hadoop configuration options\n"
exit -1
fi
