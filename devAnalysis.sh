#!/bin/bash

function displayResult {
	numberOfMinutesDisplay=$(($1 / 60))
	numberOfSecondsDisplay=$(($1 % 60))
	echo -e "Average time per commit is : $numberOfMinutesDisplay min $numberOfSecondsDisplay sec."
}

function analyseLogFile {
    numberOfSeconds=0
    numberOfCommits=0
    while read secondsBeforeCommit; do
        numberOfSeconds=$(($numberOfSeconds + $secondsBeforeCommit))
        numberOfCommits=$(($numberOfCommits + 1))
    done < $FILE_TO_ANALYSE
    averageSecondsPerCommit=$(($numberOfSeconds / $numberOfCommits))
}

FILE_TO_ANALYSE=$1
analyseLogFile
displayResult averageSecondsPerCommit