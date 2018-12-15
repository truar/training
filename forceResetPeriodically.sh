#!/bin/bash

function gitResetHard {
	git reset --hard HEAD
    git clean -fd
}

function loopInfinitly {
	while :; do
		sleep $1
		gitResetHard
	done
}

numberOfMinutes=$1

if [ "$numberOfMinutes" = "" ]
then
	numberOfMinutes=2
fi
numberOfSeconds=$(($numberOfMinutes * 60))

loopInfinitly $numberOfSeconds
