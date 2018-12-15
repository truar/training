#!/bin/bash

function gitResetHard {
	git reset --hard HEAD
    git clean -fd
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
	echo "Timer resetted"
}

function resetIfLastCommitChanged {
	localLastCommit=$(git log --format=oneline | head -n 1 | cut -f 1 -d " ")
	if [ "$localLastCommit" != "$lastCommit" ]
	then
		echo -ne "Last commit changed from $lastCommit to $localLastCommit"
		resetTimer
	fi
}

function loopInfinitly {
	while :; do
		displayRemainingSecond
		resetIfLastCommitChanged
		timer=$(($timer - 1))
		sleep 1
		if [ $timer = 0 ]
		then
			resetTimer
			echo "gitResetHard"
		fi
	done
}

numberOfMinutes=$1

if [ "$numberOfMinutes" = "" ]
then
	numberOfMinutes=2
fi
originalTimer=$(($numberOfMinutes * 60))
resetTimer

# Global last commit var.
# If the commit changed, we reset the timer
lastCommit=$(git log --format=oneline | head -n 1 | cut -f 1 -d " ")


loopInfinitly
