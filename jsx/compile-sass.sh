#!/bin/bash

DIR=$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )

cd $DIR/sass

while true; do
	inotifywait -e MOVE_SELF -e modify `find . -name '*.scss'` > /dev/null 2>&1
	sleep 0.5
	sassc master.scss ../static/css/master.css
done;
