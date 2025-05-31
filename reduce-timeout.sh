#!/bin/bash

echo "Reducing timeout values from 10 seconds to 3-5 seconds..."

# Find all Java files in pages directory and replace timeout values
find src/main/java/com/elektronicare/pages/ -name "*.java" -type f -exec sed -i 's/Duration\.ofSeconds(10)/Duration.ofSeconds(3)/g' {} \;

# Also replace any 15 second timeouts to 5 seconds
find src/main/java/com/elektronicare/pages/ -name "*.java" -type f -exec sed -i 's/Duration\.ofSeconds(15)/Duration.ofSeconds(5)/g' {} \;

echo "Timeout reduction completed!"

# Show summary of changes
echo "Summary of timeout changes:"
grep -r "Duration.ofSeconds" src/main/java/com/elektronicare/pages/ | wc -l
echo "Total Duration.ofSeconds occurrences found"

# Show unique timeout values
echo "Current timeout values in use:"
grep -r "Duration.ofSeconds" src/main/java/com/elektronicare/pages/ | sed 's/.*Duration\.ofSeconds(\([0-9]*\)).*/\1/' | sort | uniq -c