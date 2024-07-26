#!/bin/bash

# Define the array of project names
projects=(
  "Stratify"
  "Stratify.Test"
)

set -e

OUTPUT_DIR="./build/distributions"
echo "$PWD"

# Define the path to gradle.properties
gradle_properties="gradle.properties"

# Extract the version from gradle.properties
version=$(grep '^version=' "$gradle_properties" | cut -d'=' -f2)

# Check if version was found
if [ -z "$version" ]; then
  echo "Version not found in $gradle_properties"
  exit 1
fi

# Output the version
echo "Version found: $version"

./gradlew clean --no-daemon

./gradlew test --no-daemon

for project in "${projects[@]}"; do
    rm -rf ~/.m2
    ./gradlew :"$project":generateZip --no-daemon
    mkdir -p $OUTPUT_DIR
    cp ./"$project"/build/distributions/* $OUTPUT_DIR
done

# Use the version to zip files
output_file="$OUTPUT_DIR/stratify_artifacts_$version.zip"

# Create a ZIP file containing all ZIP files in the directory
zip -j "$output_file" "$OUTPUT_DIR"/*.zip

echo "Created $output_file containing all ZIP files from $OUTPUT_DIR"
