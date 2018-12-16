#!/bin/bash

function logToDevAnalysisFile {
	echo $secondsPassed >> $currentAnalysisFile
}

function createDevAnalysisFile {
	mkdir -p $DEV_ANALYSIS_REPO
	currentAnalysisFile="$DEV_ANALYSIS_REPO/analysisFile-$(eval date \"+%Y%m%d%H%M%S\").log"
	touch $currentAnalysisFile
}

function gitResetHard {
	if [ "$FAKEIT" = "1" ]
	then
		echo "GitResetHard"
	else
		git reset --hard HEAD
		git clean -fd
	fi
}

function displayRemainingSecond {
	numberOfMinutesDisplay=$(($timer / 60))
	numberOfSecondsDisplay=$(($timer % 60))
	if (( $numberOfMinutesDisplay < 10 ))
	then
		numberOfMinutesDisplay="0$numberOfMinutesDisplay"
	fi
	if (( $numberOfSecondsDisplay < 10 ))
	then
		numberOfSecondsDisplay="0$numberOfSecondsDisplay"
	fi
	echo -ne "\r$numberOfMinutesDisplay:$numberOfSecondsDisplay"
}

function resetTimer {
	timer=$originalTimer
	secondsPassed=0
	echo "Timer resetted"
}

function resetIfLastCommitChanged {
	localLastCommit=$(eval $getLastCommitCommand)
	if [ "$localLastCommit" != "$lastCommit" ]
	then
		echo -ne "\nLast commit changed from $lastCommit to $localLastCommit. "
		logToDevAnalysisFile 
		lastCommit=$localLastCommit
		resetTimer
	fi
}


function loopInfinitly {
	while :; do
		displayRemainingSecond
		resetIfLastCommitChanged
		timer=$(($timer - 1))
		secondsPassed=$(($secondsPassed + 1))
		sleep 1
		if [ $timer = 0 ]
		then
			resetTimer
			gitResetHard
		fi
	done
}

function init {
	if [ "$numberOfMinutes" = "" ]
	then
		echo "No minutes provided. Using 2 minutes as a default timer"
		numberOfMinutes=2
	fi
	echo -e "Timer set to $numberOfMinutes minutes. Have fun !"

	originalTimer=$(($numberOfMinutes * 60))
	resetTimer
	secondsPassed=0

	# Global last commit var.
	# If the commit changed, we reset the timer
	getLastCommitCommand="git log --format=oneline | head -n 1 | cut -f 1 -d \" \""
	lastCommit=$(eval $getLastCommitCommand)

	createDevAnalysisFile
}

DEV_ANALYSIS_REPO=".devAnalysis"
numberOfMinutes=$1
FAKEIT=$2
if [ "$FAKEIT" = "1" ]
then
	echo "FAKE IT MODE ACTIVATED !!"
fi

init
loopInfinitly
