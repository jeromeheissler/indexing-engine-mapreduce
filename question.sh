#! /bin/bash
#on verifie le nombre d'argument
if [ $# == 5 ]; 
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
declare QUESTION=$4;

export HADOOP_HOME=/usr/local/hadoop

# Execute the jobs
printf "\nExecuting Job 1: Word Frequency in Doc\n"
${HADOOP_HOME}/bin/hadoop jar moteurIndexation.jar question $CONFIG $INPUT $OUTPUT $QUESTION

fi

if [$# -ne 4];
then
printf "./indexation.sh [conf] <input_dir> <output_dir> <question>\n"
printf " input_dir  = repertoire contenant les fichiers a indexer\n"
printf " output_dir = repertoire de sortie\n"
printf " conf       = Fichier de configuration du programme\n"
printf " question = votre question \n"
exit -1
fi
