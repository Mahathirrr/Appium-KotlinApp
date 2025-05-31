#!/bin/bash

# Fix misplaced initializeElements() calls in all page classes

echo "Fixing misplaced initializeElements() calls..."

# Find all files with misplaced initializeElements() after return statements
find src/main/java/com/elektronicare/pages/ -name "*.java" -exec grep -l "return.*;" {} \; | while read file; do
    echo "Processing $file..."
    
    # Remove misplaced initializeElements() calls that come after return statements
    sed -i '/return.*;/{
        N
        s/return.*;\n[[:space:]]*initializeElements();/&/
        t remove_init
        b
        :remove_init
        s/\(return.*;\)\n[[:space:]]*initializeElements();/\1/
    }' "$file"
    
    # Also handle cases where initializeElements() is on the same line after return
    sed -i 's/return \(.*\); *initializeElements();/return \1;/' "$file"
done

echo "Fixed misplaced initializeElements() calls."