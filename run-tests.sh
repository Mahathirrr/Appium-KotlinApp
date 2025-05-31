#!/bin/bash

# ElektroniCare Appium Test Execution Script
# Usage: ./run-tests.sh [environment] [test-type] [test-class]

# Colors for output
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
BLUE='\033[0;34m'
NC='\033[0m' # No Color

print_usage() {
    echo "Usage: $0 [environment] [test-type] [test-class]"
    echo ""
    echo "Environment:"
    echo "  dev      - Development environment (default)"
    echo "  staging  - Staging environment"
    echo "  prod     - Production environment"
    echo ""
    echo "Test Type:"
    echo "  smoke    - Smoke tests"
    echo "  regression - Regression tests"
    echo "  ui       - UI tests"
    echo "  all      - All tests (default)"
    echo "  cucumber - Cucumber BDD tests"
    echo ""
    echo "Test Class (optional):"
    echo "  SplashTest, LoginTest, DashboardTest, etc."
    echo ""
    echo "Examples:"
    echo "  $0                           # Run all tests in dev environment"
    echo "  $0 staging smoke             # Run smoke tests in staging"
    echo "  $0 dev ui LoginTest          # Run LoginTest UI tests in dev"
    echo "  $0 prod cucumber             # Run Cucumber tests in production"
}

# Default values
ENVIRONMENT=${1:-dev}
TEST_TYPE=${2:-all}
TEST_CLASS=${3:-}

# Validate environment
if [[ ! "$ENVIRONMENT" =~ ^(dev|staging|prod)$ ]]; then
    echo -e "${RED}Error: Invalid environment '$ENVIRONMENT'${NC}"
    print_usage
    exit 1
fi

# Validate test type
if [[ ! "$TEST_TYPE" =~ ^(smoke|regression|ui|all|cucumber)$ ]]; then
    echo -e "${RED}Error: Invalid test type '$TEST_TYPE'${NC}"
    print_usage
    exit 1
fi

echo -e "${BLUE}🚀 ElektroniCare Test Execution${NC}"
echo "=================================="
echo "Environment: $ENVIRONMENT"
echo "Test Type: $TEST_TYPE"
if [ ! -z "$TEST_CLASS" ]; then
    echo "Test Class: $TEST_CLASS"
fi
echo ""

# Check prerequisites
echo -e "${YELLOW}Checking prerequisites...${NC}"

# Check if Appium server is running
if ! curl -s http://localhost:4723/wd/hub/status > /dev/null; then
    echo -e "${RED}Error: Appium server is not running${NC}"
    echo "Start Appium server with: appium --port 4723"
    exit 1
fi

# Check if device is connected
DEVICES=$(adb devices | grep -v "List of devices" | grep -v "^$" | wc -l)
if [ $DEVICES -eq 0 ]; then
    echo -e "${RED}Error: No devices connected${NC}"
    echo "Connect a device or start an emulator"
    exit 1
fi

# Check if APK is installed
if ! adb shell pm list packages | grep -q "elektronicare"; then
    echo -e "${YELLOW}Warning: ElektroniCare APK not found. Installing...${NC}"
    if [ -f "ElektroniCareBeta1-Updated.apk" ]; then
        adb install -r ElektroniCareBeta1-Updated.apk
    else
        echo -e "${RED}Error: APK file not found${NC}"
        exit 1
    fi
fi

echo -e "${GREEN}Prerequisites check passed!${NC}"
echo ""

# Compile project
echo -e "${YELLOW}Compiling project...${NC}"
mvn clean compile test-compile -q
if [ $? -ne 0 ]; then
    echo -e "${RED}Error: Compilation failed${NC}"
    exit 1
fi

# Prepare Maven command
MVN_CMD="mvn test"

# Add environment configuration
MVN_CMD="$MVN_CMD -Denv=$ENVIRONMENT"

# Add test type configuration
case $TEST_TYPE in
    "smoke")
        MVN_CMD="$MVN_CMD -Dgroups=smoke"
        ;;
    "regression")
        MVN_CMD="$MVN_CMD -Dgroups=regression"
        ;;
    "ui")
        MVN_CMD="$MVN_CMD -Dgroups=ui"
        ;;
    "cucumber")
        MVN_CMD="$MVN_CMD -Dtest=TestRunner"
        ;;
    "all")
        # Run all tests
        ;;
esac

# Add specific test class if provided
if [ ! -z "$TEST_CLASS" ] && [ "$TEST_TYPE" != "cucumber" ]; then
    MVN_CMD="$MVN_CMD -Dtest=$TEST_CLASS"
fi

# Execute tests
echo -e "${YELLOW}Executing tests...${NC}"
echo "Command: $MVN_CMD"
echo ""

# Create timestamp for this test run
TIMESTAMP=$(date +"%Y%m%d_%H%M%S")
LOG_FILE="test-execution-$TIMESTAMP.log"

# Run tests and capture output
$MVN_CMD | tee $LOG_FILE

TEST_RESULT=$?

echo ""
echo "=================================="

# Check test results
if [ $TEST_RESULT -eq 0 ]; then
    echo -e "${GREEN}✅ Tests completed successfully!${NC}"
else
    echo -e "${RED}❌ Tests failed!${NC}"
fi

# Display report locations
echo ""
echo -e "${BLUE}📊 Test Reports:${NC}"
echo "TestNG Report: target/surefire-reports/index.html"
if [ "$TEST_TYPE" == "cucumber" ]; then
    echo "Cucumber Report: target/cucumber-reports/index.html"
fi
echo "Screenshots: test-output/screenshots/"
echo "Execution Log: $LOG_FILE"

# Display test summary
echo ""
echo -e "${BLUE}📈 Test Summary:${NC}"
if [ -f "target/surefire-reports/testng-results.xml" ]; then
    TOTAL=$(grep -o 'total="[0-9]*"' target/surefire-reports/testng-results.xml | grep -o '[0-9]*')
    PASSED=$(grep -o 'passed="[0-9]*"' target/surefire-reports/testng-results.xml | grep -o '[0-9]*')
    FAILED=$(grep -o 'failed="[0-9]*"' target/surefire-reports/testng-results.xml | grep -o '[0-9]*')
    SKIPPED=$(grep -o 'skipped="[0-9]*"' target/surefire-reports/testng-results.xml | grep -o '[0-9]*')
    
    echo "Total: $TOTAL"
    echo -e "Passed: ${GREEN}$PASSED${NC}"
    if [ "$FAILED" -gt 0 ]; then
        echo -e "Failed: ${RED}$FAILED${NC}"
    else
        echo "Failed: $FAILED"
    fi
    echo "Skipped: $SKIPPED"
fi

# Open reports if on macOS
if [[ "$OSTYPE" == "darwin"* ]]; then
    echo ""
    read -p "Open test reports in browser? (y/n): " OPEN_REPORTS
    if [ "$OPEN_REPORTS" = "y" ] || [ "$OPEN_REPORTS" = "Y" ]; then
        open target/surefire-reports/index.html
        if [ "$TEST_TYPE" == "cucumber" ] && [ -f "target/cucumber-reports/index.html" ]; then
            open target/cucumber-reports/index.html
        fi
    fi
fi

echo ""
echo -e "${BLUE}Happy Testing! 🚀${NC}"

exit $TEST_RESULT