#!/usr/bin/env bash
./wait-for-it.sh localhost:8761
CHILD=$?
if [[ $CHILD -gt 0 ]]; then
    echo "discovery-service not started. Start it first"
    exit $CHILD
else
    docker run --rm -it -h bowl-service --network game_single-tier -p 8083:8083 bowl-service --link discovery-service --name my-bowl-service
fi
