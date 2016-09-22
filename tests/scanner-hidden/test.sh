#!/bin/sh

runscanner() {
    curdir=$PWD
    cd `dirname $1`
    $(git rev-parse --show-toplevel)/run.sh -t scan `basename $1`
    cd $curdir
}

fail=0

for file in `dirname $0`/input/*; do
  output=`tempfile`
  runscanner $file > $output 2>&1;
  if ! diff -u $output `dirname $0`/output/`basename $file`.out; then
    echo "File $file scanner output mismatch.";
    fail=1
  fi
  rm $output;
done

exit $fail;
