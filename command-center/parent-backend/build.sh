#!/bin/bash
# Script to find and build Maven projects

# Print some debug info
echo "Current directory: $(pwd)"
echo "Directory contents:"
ls -la

# Find all pom.xml files
POM_FILES=$(find . -name "pom.xml")

if [ -z "$POM_FILES" ]; then
  echo "ERROR: No pom.xml found in the project!"
  exit 1
fi

echo "Found the following pom.xml files:"
echo "$POM_FILES"

# Use the first pom.xml found (or the root one if there are multiple)
ROOT_POM=""
for POM in $POM_FILES; do
  if [ "$POM" = "./pom.xml" ]; then
    ROOT_POM="$POM"
    break
  fi
done

# If no root pom.xml was found, use the first one
if [ -z "$ROOT_POM" ]; then
  ROOT_POM=$(echo "$POM_FILES" | head -1)
fi

echo "Using POM file: $ROOT_POM"
POM_DIR=$(dirname "$ROOT_POM")

# Change to the directory containing the pom.xml and build
cd "$POM_DIR"
echo "Building from directory: $(pwd)"
mvn clean package -DskipTests

# Check if the build was successful
if [ $? -ne 0 ]; then
  echo "Maven build failed!"
  exit 1
fi

echo "Build successful!"