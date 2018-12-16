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
	localLastCommit=$(eval $getLastCommitCommand)
	if [ "$localLastCommit" != "$lastCommit" ]
	then
		echo -ne "\nLast commit changed from $lastCommit to $localLastCommit. "
		lastCommit=$localLastCommit
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
	echo "No minutes provided. Using 2 minutes as a default timer"
	numberOfMinutes=2
fi
echo -e "Timer set to $numberOfMinutes minutes. Have fun !"

originalTimer=$(($numberOfMinutes * 60))
resetTimer

# Global last commit var.
# If the commit changed, we reset the timer
getLastCommitCommand="git log --format=oneline | head -n 1 | cut -f 1 -d \" \""
lastCommit=$(eval $getLastCommitCommand)

loopInfinitly
