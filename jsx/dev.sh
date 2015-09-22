#!/bin/bash

DIR=$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )

cd $DIR

LD_PRELOAD="/usr/lib/libjemalloc.so" gulp&
./compile-sass.sh&
(cd static; LD_PRELOAD="/usr/lib/libjemalloc.so" python -m http.server)&

int(){
	kill -- -$$
}
trap int SIGINT

wait
