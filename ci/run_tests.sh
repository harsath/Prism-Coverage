#!/usr/bin/env bash

BIN_OUT="classes"

export CLASSPATH=".:${PWD}/lib/antlr-4.11.1-complete.jar"
mkdir $BIN_OUT
javac -d $BIN_OUT -cp lib/antlr-4.11.1-complete.jar:lib/junit-4.13.2.jar src/antlr/*.java src/prism/*.java src/prism_interpreter/*.java src/prism_coverage/*.java src/app/*.java src/tests/*.java
cd $BIN_OUT
java tests.TestRunner
