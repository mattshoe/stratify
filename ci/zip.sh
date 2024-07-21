#!/bin/bash

# Define the array of project names
projects=(
  "Stratify"
)

OUTPUT_DIR="./build/distributions/"
echo "$PWD"

./gradlew clean --no-daemon

./gradlew test --no-daemon

for project in "${projects[@]}"; do
    rm -rf ~/.m2
    ./gradlew :"$project":generateZip --no-daemon
    mkdir -p $OUTPUT_DIR
    cp ./"$project"/build/distributions/* $OUTPUT_DIR
done


