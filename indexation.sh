#! /bin/bash
#on verifie le nombre d'argument
if [ $# == 4 ]; 
then 
#on verifie si le fichier output n'existe pas deja sinon on le supprime
if [ -d $3 ];
then
rm -r $3/*;
fi

# Get the input data
declare INPUT=$2;
declare OUTPUT=$3;
declare CONFIG=$1;

export HADOOP_HOME=/usr/local/hadoop

# Execute the jobs
printf "\nExecuting Job 1: Word Frequency in Doc\n"
${HADOOP_HOME}/bin/hadoop jar moteurIndexation.jar index $CONFIG $INPUT $OUTPUT

fi

if [$# -ne 4];
then
printf "./indexation.sh [conf] <input_dir> <output_dir> \n"
printf " input_dir  = Directory with the input files\n"
printf " output_dir = Directory to place the output\n"
printf " conf       = Optional hadoop configuration options\n"
exit -1
fi
