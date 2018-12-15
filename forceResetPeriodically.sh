#!/bin/bash

function gitResetHard {
	git reset --hard HEAD
    git clean -fd
}

function loopInfinitly {
	numberOfSecondRemaining=$1
	while :; do
		numberOfMinutesDisplay=$(($numberOfSecondRemaining / 60))
		numberOfSecondsDisplay=$(($numberOfSecondRemaining % 60))
		echo -ne "\r$numberOfMinutesDisplay:$numberOfSecondsDisplay"
		numberOfSecondRemaining=$(($numberOfSecondRemaining - 1))
		sleep 1
		if [ $numberOfSecondRemaining = 0 ]
		then
			numberOfSecondRemaining=$1
			echo ""
			gitResetHard
		fi
	done
}

numberOfMinutes=$1

if [ "$numberOfMinutes" = "" ]
then
	numberOfMinutes=2
fi
numberOfSeconds=$(($numberOfMinutes * 60))

loopInfinitly $numberOfSeconds
