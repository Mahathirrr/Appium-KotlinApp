@echo off
REM ElektroniCare Appium Test Framework Setup Script for Windows
REM This script helps automate the setup process

echo.
echo 🚀 ElektroniCare Appium Test Framework Setup
echo =============================================
echo.

REM Step 1: Check Prerequisites
echo [STEP] 1. Checking Prerequisites...

REM Check Java
java -version >nul 2>&1
if %errorlevel% neq 0 (
    echo [ERROR] Java not found. Please install JDK 17 or higher.
    pause
    exit /b 1
) else (
    echo [INFO] Java found
)

REM Check Maven
mvn --version >nul 2>&1
if %errorlevel% neq 0 (
    echo [ERROR] Maven not found. Please install Maven.
    pause
    exit /b 1
) else (
    echo [INFO] Maven found
)

REM Check Node.js
node --version >nul 2>&1
if %errorlevel% neq 0 (
    echo [ERROR] Node.js not found. Please install Node.js.
    pause
    exit /b 1
) else (
    echo [INFO] Node.js found
)

REM Check ADB
adb version >nul 2>&1
if %errorlevel% neq 0 (
    echo [ERROR] ADB not found. Please install Android SDK.
    pause
    exit /b 1
) else (
    echo [INFO] ADB found
)

REM Check Appium
appium --version >nul 2>&1
if %errorlevel% neq 0 (
    echo [WARNING] Appium not found. Installing Appium...
    npm install -g appium
    appium driver install uiautomator2
) else (
    echo [INFO] Appium found
)

echo.

REM Step 2: Check Device Connection
echo [STEP] 2. Checking Device Connection...

adb devices | find "device" >nul
if %errorlevel% neq 0 (
    echo [WARNING] No devices found. Please connect a device or start an emulator.
    echo To start an emulator:
    echo   emulator -list-avds
    echo   emulator -avd YOUR_AVD_NAME
) else (
    echo [INFO] Device(s) found:
    adb devices
)

echo.

REM Step 3: Install APK
echo [STEP] 3. Installing ElektroniCare APK...

if exist "ElektroniCareBeta1-Updated.apk" (
    echo [INFO] Installing APK...
    adb install -r ElektroniCareBeta1-Updated.apk
    
    REM Verify installation
    adb shell pm list packages | find "elektronicare" >nul
    if %errorlevel% equ 0 (
        echo [INFO] APK installed successfully!
    ) else (
        echo [ERROR] APK installation failed.
    )
) else (
    echo [ERROR] APK file not found: ElektroniCareBeta1-Updated.apk
)

echo.

REM Step 4: Compile Project
echo [STEP] 4. Compiling Test Framework...

echo [INFO] Cleaning previous builds...
mvn clean

echo [INFO] Compiling source code...
mvn compile test-compile
if %errorlevel% neq 0 (
    echo [ERROR] Compilation failed. Please check the errors above.
    pause
    exit /b 1
) else (
    echo [INFO] Compilation successful!
)

echo.

REM Step 5: Start Appium Server (optional)
echo [STEP] 5. Appium Server Setup...

set /p START_APPIUM="Do you want to start Appium server now? (y/n): "
if /i "%START_APPIUM%"=="y" (
    echo [INFO] Starting Appium server on port 4723...
    echo Note: Appium server will run in a new window. Close the window to stop it.
    start "Appium Server" appium --port 4723 --log-level info
    timeout /t 3 >nul
    
    REM Check if server started
    curl -s http://localhost:4723/wd/hub/status >nul 2>&1
    if %errorlevel% equ 0 (
        echo [INFO] Appium server started successfully!
    ) else (
        echo [WARNING] Could not verify Appium server status. Check the Appium window.
    )
) else (
    echo [WARNING] Appium server not started. Start it manually with: appium --port 4723
)

echo.

REM Step 6: Test Execution Options
echo [STEP] 6. Test Execution Options
echo.
echo Setup completed! Here are your test execution options:
echo.
echo 📋 Quick Test Commands:
echo   mvn test -Dtest=SplashTest              # Test splash screen
echo   mvn test -Dtest=LoginTest               # Test login functionality
echo   mvn test -Dtest=DashboardTest           # Test dashboard
echo   mvn test -Dgroups=smoke                 # Run smoke tests
echo   mvn test                                # Run all tests
echo.
echo 🥒 Cucumber BDD Tests:
echo   mvn test -Dtest=TestRunner              # Run all BDD scenarios
echo   mvn test -Dtest=TestRunner -Dcucumber.options="--tags @onboarding"
echo   mvn test -Dtest=TestRunner -Dcucumber.options="--tags @welcome"
echo   mvn test -Dtest=TestRunner -Dcucumber.options="--tags @register"
echo.
echo 📊 View Reports:
echo   start target\surefire-reports\index.html     # TestNG reports
echo   start target\cucumber-reports\index.html     # Cucumber reports
echo.

REM Step 7: Quick Test
set /p RUN_TEST="Do you want to run a quick smoke test now? (y/n): "
if /i "%RUN_TEST%"=="y" (
    echo [STEP] 7. Running Quick Smoke Test...
    
    mvn test -Dtest=SplashTest -q
    if %errorlevel% equ 0 (
        echo [INFO] Smoke test passed! Framework is ready.
    ) else (
        echo [WARNING] Smoke test failed. Check the setup and try again.
    )
)

echo.
echo [INFO] Setup completed successfully! 🎉
echo.
echo 📖 For detailed instructions, see: SETUP_GUIDE.md
echo 🐛 For troubleshooting, check the logs in target\surefire-reports\
echo.
echo Happy Testing! 🚀
echo.
pause