#!/bin/bash

# Script to fix all test files by adding proper createTest calls

cd /workspace/ElektroniCareBeta2/Appium

# Fix all remaining ExtentReportManager calls in all test files
find src/test/java -name "*.java" -exec sed -i 's/ExtentReportManager\.getTest()\.info(/logInfo(/g; s/ExtentReportManager\.getTest()\.pass(/logPass(/g; s/ExtentReportManager\.getTest()\.fail(/logFail(/g; s/ExtentReportManager\.getTest()\.skip(/logSkip(/g' {} \;

echo "Fixed ExtentReportManager calls in all test files"

# Add createTest calls to test methods that don't have them
# This will add createTest before each logInfo that doesn't already have createTest before it

# For LoginTest
if [ -f "src/test/java/com/elektronicare/tests/LoginTest.java" ]; then
    sed -i '/public void.*Test.*() {$/,/logInfo/ {
        /logInfo/ {
            /createTest/! i\        createTest("Test Method", "Test Description");
        }
    }' src/test/java/com/elektronicare/tests/LoginTest.java
fi

# For DashboardTest  
if [ -f "src/test/java/com/elektronicare/tests/DashboardTest.java" ]; then
    sed -i '/public void.*Test.*() {$/,/logInfo/ {
        /logInfo/ {
            /createTest/! i\        createTest("Test Method", "Test Description");
        }
    }' src/test/java/com/elektronicare/tests/DashboardTest.java
fi

# For ServicesTest
if [ -f "src/test/java/com/elektronicare/tests/ServicesTest.java" ]; then
    sed -i '/public void.*Test.*() {$/,/logInfo/ {
        /logInfo/ {
            /createTest/! i\        createTest("Test Method", "Test Description");
        }
    }' src/test/java/com/elektronicare/tests/ServicesTest.java
fi

echo "Added createTest calls to test methods"
echo "Test files fixed successfully!"