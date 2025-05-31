#!/bin/bash

# ElektroniCare Appium Test Execution with Logging
# This script runs the Appium tests and saves all output to log files

# Create logs directory if it doesn't exist
mkdir -p logs

# Get current timestamp for log file naming
TIMESTAMP=$(date +"%Y%m%d_%H%M%S")

echo "Starting ElektroniCare Appium Test Execution..."
echo "Timestamp: $TIMESTAMP"
echo "Logs will be saved to: logs/test_execution_$TIMESTAMP.log"

# Run all tests and save output to log file
echo "=== ElektroniCare Appium Test Execution Started at $(date) ===" > logs/test_execution_$TIMESTAMP.log
echo "Test Environment: $(uname -a)" >> logs/test_execution_$TIMESTAMP.log
echo "Java Version: $(java -version 2>&1)" >> logs/test_execution_$TIMESTAMP.log
echo "Maven Version: $(mvn -version 2>&1 | head -1)" >> logs/test_execution_$TIMESTAMP.log
echo "========================================" >> logs/test_execution_$TIMESTAMP.log
echo "" >> logs/test_execution_$TIMESTAMP.log

# Run Maven test with full output logging
mvn clean test -Dtest.output.verbose=true 2>&1 | tee -a logs/test_execution_$TIMESTAMP.log

# Add completion timestamp
echo "" >> logs/test_execution_$TIMESTAMP.log
echo "=== Test Execution Completed at $(date) ===" >> logs/test_execution_$TIMESTAMP.log

echo "Test execution completed. Check logs/test_execution_$TIMESTAMP.log for detailed results."

# Show summary
echo ""
echo "=== EXECUTION SUMMARY ==="
echo "Log file: logs/test_execution_$TIMESTAMP.log"
echo "Log size: $(du -h logs/test_execution_$TIMESTAMP.log | cut -f1)"
echo "=========================="