#!/bin/bash

runcompiler() {
    $(git rev-parse --show-toplevel)/run.sh --opt=all --target=assembly -o $2 $1
}

fail=0

if ! gcc -v 2>&1 |grep -q '^Target: x86_64-linux-gnu'; then
  echo "Refusing to run cross-compilation on non-64-bit architechure."
  exit 0;
fi

for file in `dirname $0`/input/*.dcf; do
  echo "Running file $file"
  asm=`tempfile --suffix=.s`
  msg=""
  if runcompiler $file $asm 2>&1 >/dev/null; then
    binary=`tempfile`
    if gcc -o $binary -L `dirname $0`/lib -l6035 $asm 2>&1 >/dev/null; then
      output=`tempfile`
      $binary > $output
      exitcode=$?
      diffout=`tempfile`
      if [ -f `dirname $0`/error/`basename $file`.err ]; then
        val=$(<`dirname $0`/error/`basename $file`.err)
        if [ "$val" != "$exitcode" ]; then
          msg="Program did not exit with exit status $val"
        fi
      else
        if ! diff -u $output `dirname $0`/output/`basename $file`.out > $diffout 2>/dev/null; then
          msg="File $file output mismatch.";
        fi
      fi
    else
      msg="Program failed to assemble.";
    fi
  else
    msg="Program failed to generate assembly.";
  fi
  if [ ! -z "$msg" ]; then
    fail=1
    echo $msg
  fi
  rm -f $diffout $output $binary $asm;
done

exit $fail;
