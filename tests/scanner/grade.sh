#!/bin/bash

runscanner() {
    curdir=$PWD
    cd `dirname $1`
    $(git rev-parse --show-toplevel)/run.sh -t scan `basename $1`
    cd $curdir
}

fail=0
failedFiles=""

for file in `dirname $0`/input/*; do
  output=`tempfile`
  runscanner $file > $output 2>&1;
  if ! diff -u $output `dirname $0`/output/`basename $file`.out; then
    let "fail += 1"

    printf "Failed test $file \n\n\n\n\n\n"
    failedFiles="$failedFiles$file\n"

for file in `dirname $0`/input/*; do
  output=`tempfile`
  runscanner $file > $output 2>&1;
  if ! diff -u $output `dirname $0`/output/`basename $file`.out; then
    let "fail += 1"
    echo "Failed test $file"
  fi
  rm $output;
done

#clean up the screen since there
#is a lot of output
clear

#print out the number of tests failed
#and the names of the tests that failed
numfiles=(./tests/scanner/input/*)
numfiles=${#numfiles[@]}
printf "Failed $fail/$numfiles tests:\n"
printf "$failedFiles"
exit $fail;

