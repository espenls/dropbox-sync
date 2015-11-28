#!/bin/bash

pushd /home/pi/photoframe/sync/
cp `ls |shuf|head -1` /home/pi/photoframe/current.jpg ; popd