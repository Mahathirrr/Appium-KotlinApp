#!/bin/bash

# ElektroniCare Appium Test Framework Setup Script
# This script helps automate the setup process

echo "🚀 ElektroniCare Appium Test Framework Setup"
echo "============================================="

# Colors for output
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
BLUE='\033[0;34m'
NC='\033[0m' # No Color

# Function to print colored output
print_status() {
    echo -e "${GREEN}[INFO]${NC} $1"
}

print_warning() {
    echo -e "${YELLOW}[WARNING]${NC} $1"
}

print_error() {
    echo -e "${RED}[ERROR]${NC} $1"
}

print_step() {
    echo -e "${BLUE}[STEP]${NC} $1"
}

# Check if command exists
command_exists() {
    command -v "$1" >/dev/null 2>&1
}

# Step 1: Check Prerequisites
print_step "1. Checking Prerequisites..."

# Check Java
if command_exists java; then
    JAVA_VERSION=$(java -version 2>&1 | head -n 1 | cut -d'"' -f2)
    print_status "Java found: $JAVA_VERSION"
else
    print_error "Java not found. Please install JDK 17 or higher."
    exit 1
fi

# Check Maven
if command_exists mvn; then
    MVN_VERSION=$(mvn --version | head -n 1)
    print_status "Maven found: $MVN_VERSION"
else
    print_error "Maven not found. Please install Maven."
    exit 1
fi

# Check Node.js
if command_exists node; then
    NODE_VERSION=$(node --version)
    print_status "Node.js found: $NODE_VERSION"
else
    print_error "Node.js not found. Please install Node.js."
    exit 1
fi

# Check ADB
if command_exists adb; then
    ADB_VERSION=$(adb version | head -n 1)
    print_status "ADB found: $ADB_VERSION"
else
    print_error "ADB not found. Please install Android SDK."
    exit 1
fi

# Check Appium
if command_exists appium; then
    APPIUM_VERSION=$(appium --version)
    print_status "Appium found: $APPIUM_VERSION"
else
    print_warning "Appium not found. Installing Appium..."
    npm install -g appium
    appium driver install uiautomator2
fi

# Step 2: Check Device Connection
print_step "2. Checking Device Connection..."

DEVICES=$(adb devices | grep -v "List of devices" | grep -v "^$" | wc -l)
if [ $DEVICES -gt 0 ]; then
    print_status "Found $DEVICES connected device(s):"
    adb devices
else
    print_warning "No devices found. Please connect a device or start an emulator."
    echo "To start an emulator:"
    echo "  emulator -list-avds"
    echo "  emulator -avd YOUR_AVD_NAME"
fi

# Step 3: Install APK
print_step "3. Installing ElektroniCare APK..."

if [ -f "ElektroniCareBeta1-Updated.apk" ]; then
    print_status "Installing APK..."
    adb install -r ElektroniCareBeta1-Updated.apk
    
    # Verify installation
    if adb shell pm list packages | grep -q "elektronicare"; then
        print_status "APK installed successfully!"
    else
        print_error "APK installation failed."
    fi
else
    print_error "APK file not found: ElektroniCareBeta1-Updated.apk"
fi

# Step 4: Compile Project
print_step "4. Compiling Test Framework..."

print_status "Cleaning previous builds..."
mvn clean

print_status "Compiling source code..."
if mvn compile test-compile; then
    print_status "Compilation successful!"
else
    print_error "Compilation failed. Please check the errors above."
    exit 1
fi

# Step 5: Start Appium Server (optional)
print_step "5. Appium Server Setup..."

echo "Do you want to start Appium server now? (y/n)"
read -r START_APPIUM

if [ "$START_APPIUM" = "y" ] || [ "$START_APPIUM" = "Y" ]; then
    print_status "Starting Appium server on port 4723..."
    echo "Note: Appium server will run in background. Use 'pkill -f appium' to stop it."
    nohup appium --port 4723 --log-level info > appium.log 2>&1 &
    sleep 3
    
    # Check if server started
    if curl -s http://localhost:4723/wd/hub/status > /dev/null; then
        print_status "Appium server started successfully!"
    else
        print_error "Failed to start Appium server. Check appium.log for details."
    fi
else
    print_warning "Appium server not started. Start it manually with: appium --port 4723"
fi

# Step 6: Test Execution Options
print_step "6. Test Execution Options"

echo ""
echo "Setup completed! Here are your test execution options:"
echo ""
echo "📋 Quick Test Commands:"
echo "  mvn test -Dtest=SplashTest              # Test splash screen"
echo "  mvn test -Dtest=LoginTest               # Test login functionality"
echo "  mvn test -Dtest=DashboardTest           # Test dashboard"
echo "  mvn test -Dgroups=smoke                 # Run smoke tests"
echo "  mvn test                                # Run all tests"
echo ""
echo "🥒 Cucumber BDD Tests:"
echo "  mvn test -Dtest=TestRunner              # Run all BDD scenarios"
echo "  mvn test -Dtest=TestRunner -Dcucumber.options=\"--tags @onboarding\""
echo "  mvn test -Dtest=TestRunner -Dcucumber.options=\"--tags @welcome\""
echo "  mvn test -Dtest=TestRunner -Dcucumber.options=\"--tags @register\""
echo ""
echo "📊 View Reports:"
echo "  open target/surefire-reports/index.html     # TestNG reports"
echo "  open target/cucumber-reports/index.html     # Cucumber reports"
echo ""

# Step 7: Quick Test
echo "Do you want to run a quick smoke test now? (y/n)"
read -r RUN_TEST

if [ "$RUN_TEST" = "y" ] || [ "$RUN_TEST" = "Y" ]; then
    print_step "7. Running Quick Smoke Test..."
    
    if mvn test -Dtest=SplashTest -q; then
        print_status "Smoke test passed! Framework is ready."
    else
        print_warning "Smoke test failed. Check the setup and try again."
    fi
fi

echo ""
print_status "Setup completed successfully! 🎉"
echo ""
echo "📖 For detailed instructions, see: SETUP_GUIDE.md"
echo "🐛 For troubleshooting, check the logs in target/surefire-reports/"
echo ""
echo "Happy Testing! 🚀"