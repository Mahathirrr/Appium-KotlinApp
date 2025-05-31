#!/bin/bash

# Script to add initializeElements() to all public methods in page classes

cd /workspace/ElektroniCareBeta2/Appium

# Find all page classes and add initializeElements() to public methods that use driver
find src/main/java/com/elektronicare/pages -name "*.java" -not -name "BasePage.java" | while read file; do
    echo "Processing $file"
    
    # Add initializeElements() after public method declarations that likely use driver
    # This targets methods that have driver operations
    sed -i '/public.*{$/,/try\|driver\|wait\|element/ {
        /public.*{$/ {
            N
            /public.*{$\n.*try\|driver\|wait\|element/ {
                s/public.*{$/&\n        initializeElements();/
            }
        }
    }' "$file"
    
    # Also add to methods that directly access WebElements
    sed -i '/public.*{$/,/\.\(click\|sendKeys\|isDisplayed\|getText\)/ {
        /public.*{$/ {
            N
            /public.*{$\n.*\.\(click\|sendKeys\|isDisplayed\|getText\)/ {
                s/public.*{$/&\n        initializeElements();/
            }
        }
    }' "$file"
done

echo "Added initializeElements() to page classes"