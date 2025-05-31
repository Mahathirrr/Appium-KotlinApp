#!/bin/bash

# ElektroniCare Appium - Run Specific Test Suites with Logging

# Function to display usage
show_usage() {
    echo "Usage: $0 [TEST_SUITE] [LOG_LEVEL]"
    echo ""
    echo "Available Test Suites:"
    echo "  smoke       - Run smoke tests only"
    echo "  regression  - Run full regression tests"
    echo "  ui          - Run UI tests only"
    echo "  functional  - Run functional tests only"
    echo "  navigation  - Run navigation tests only"
    echo "  all         - Run all test suites (default)"
    echo ""
    echo "Available Log Levels:"
    echo "  verbose     - Detailed logging (default)"
    echo "  normal      - Standard logging"
    echo "  quiet       - Minimal logging"
    echo ""
    echo "Examples:"
    echo "  $0 smoke verbose"
    echo "  $0 regression normal"
    echo "  $0 ui"
}

# Set default values
TEST_SUITE=${1:-all}
LOG_LEVEL=${2:-verbose}

# Create logs directory
mkdir -p logs

# Get timestamp
TIMESTAMP=$(date +"%Y%m%d_%H%M%S")
LOG_FILE="logs/test_${TEST_SUITE}_${TIMESTAMP}.log"

echo "=== ElektroniCare Appium Test Execution ==="
echo "Test Suite: $TEST_SUITE"
echo "Log Level: $LOG_LEVEL"
echo "Log File: $LOG_FILE"
echo "Started at: $(date)"
echo "==========================================="

# Create log file header
{
    echo "=== ElektroniCare Appium Test Execution ==="
    echo "Test Suite: $TEST_SUITE"
    echo "Log Level: $LOG_LEVEL"
    echo "Started at: $(date)"
    echo "Environment: $(uname -a)"
    echo "Java Version: $(java -version 2>&1 | head -1)"
    echo "Maven Version: $(mvn -version 2>&1 | head -1)"
    echo "==========================================="
    echo ""
} > "$LOG_FILE"

# Set Maven options based on log level
case $LOG_LEVEL in
    verbose)
        MAVEN_OPTS="-Dtest.output.verbose=true -X"
        ;;
    normal)
        MAVEN_OPTS="-Dtest.output.verbose=true"
        ;;
    quiet)
        MAVEN_OPTS="-q"
        ;;
    *)
        MAVEN_OPTS="-Dtest.output.verbose=true"
        ;;
esac

# Run tests based on suite selection
case $TEST_SUITE in
    smoke)
        echo "Running Smoke Tests..." | tee -a "$LOG_FILE"
        mvn test $MAVEN_OPTS -Dgroups=smoke 2>&1 | tee -a "$LOG_FILE"
        ;;
    regression)
        echo "Running Full Regression Tests..." | tee -a "$LOG_FILE"
        mvn test $MAVEN_OPTS -Dsuite=regression 2>&1 | tee -a "$LOG_FILE"
        ;;
    ui)
        echo "Running UI Tests..." | tee -a "$LOG_FILE"
        mvn test $MAVEN_OPTS -Dgroups=ui 2>&1 | tee -a "$LOG_FILE"
        ;;
    functional)
        echo "Running Functional Tests..." | tee -a "$LOG_FILE"
        mvn test $MAVEN_OPTS -Dgroups=functional 2>&1 | tee -a "$LOG_FILE"
        ;;
    navigation)
        echo "Running Navigation Tests..." | tee -a "$LOG_FILE"
        mvn test $MAVEN_OPTS -Dgroups=navigation 2>&1 | tee -a "$LOG_FILE"
        ;;
    all)
        echo "Running All Tests..." | tee -a "$LOG_FILE"
        mvn clean test $MAVEN_OPTS 2>&1 | tee -a "$LOG_FILE"
        ;;
    *)
        echo "Invalid test suite: $TEST_SUITE" | tee -a "$LOG_FILE"
        show_usage
        exit 1
        ;;
esac

# Add completion info
{
    echo ""
    echo "=== Test Execution Completed ==="
    echo "Completed at: $(date)"
    echo "Log file: $LOG_FILE"
    echo "Log size: $(du -h "$LOG_FILE" | cut -f1)"
} | tee -a "$LOG_FILE"

echo ""
echo "Test execution completed successfully!"
echo "Check $LOG_FILE for detailed results."