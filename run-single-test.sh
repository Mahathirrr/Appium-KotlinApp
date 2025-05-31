#!/bin/bash

# ElektroniCare Appium - Run Single Test Class with Logging

# Function to display usage
show_usage() {
    echo "Usage: $0 [TEST_CLASS]"
    echo ""
    echo "Available Test Classes:"
    echo "  OnboardingTest   - Test onboarding functionality"
    echo "  WelcomeTest      - Test welcome screen"
    echo "  LoginTest        - Test login functionality"
    echo "  RegisterTest     - Test registration functionality"
    echo "  DashboardTest    - Test dashboard functionality"
    echo "  ServicesTest     - Test services functionality"
    echo "  HistoryTest      - Test history functionality"
    echo ""
    echo "Examples:"
    echo "  $0 LoginTest"
    echo "  $0 DashboardTest"
}

# Check if test class is provided
if [ $# -eq 0 ]; then
    echo "Error: Test class not specified"
    show_usage
    exit 1
fi

TEST_CLASS=$1

# Validate test class
case $TEST_CLASS in
    OnboardingTest|WelcomeTest|LoginTest|RegisterTest|DashboardTest|ServicesTest|HistoryTest)
        ;;
    *)
        echo "Error: Invalid test class: $TEST_CLASS"
        show_usage
        exit 1
        ;;
esac

# Create logs directory
mkdir -p logs

# Get timestamp
TIMESTAMP=$(date +"%Y%m%d_%H%M%S")
LOG_FILE="logs/${TEST_CLASS}_${TIMESTAMP}.log"

echo "=== ElektroniCare Single Test Execution ==="
echo "Test Class: $TEST_CLASS"
echo "Log File: $LOG_FILE"
echo "Started at: $(date)"
echo "=========================================="

# Create log file header
{
    echo "=== ElektroniCare Single Test Execution ==="
    echo "Test Class: $TEST_CLASS"
    echo "Started at: $(date)"
    echo "Environment: $(uname -a)"
    echo "Java Version: $(java -version 2>&1 | head -1)"
    echo "Maven Version: $(mvn -version 2>&1 | head -1)"
    echo "=========================================="
    echo ""
} > "$LOG_FILE"

# Run the specific test class
echo "Running $TEST_CLASS..." | tee -a "$LOG_FILE"
mvn test -Dtest="com.elektronicare.tests.$TEST_CLASS" -Dtest.output.verbose=true 2>&1 | tee -a "$LOG_FILE"

# Add completion info
{
    echo ""
    echo "=== Test Execution Completed ==="
    echo "Completed at: $(date)"
    echo "Log file: $LOG_FILE"
    echo "Log size: $(du -h "$LOG_FILE" | cut -f1)"
} | tee -a "$LOG_FILE"

echo ""
echo "Test execution completed!"
echo "Check $LOG_FILE for detailed results."