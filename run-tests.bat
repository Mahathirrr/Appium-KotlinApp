@echo off
REM ElektroniCare Appium Test Execution Script for Windows
REM Usage: run-tests.bat [environment] [test-type] [test-class]

setlocal enabledelayedexpansion

REM Default values
set ENVIRONMENT=%1
set TEST_TYPE=%2
set TEST_CLASS=%3

if "%ENVIRONMENT%"=="" set ENVIRONMENT=dev
if "%TEST_TYPE%"=="" set TEST_TYPE=all

echo.
echo 🚀 ElektroniCare Test Execution
echo ==================================
echo Environment: %ENVIRONMENT%
echo Test Type: %TEST_TYPE%
if not "%TEST_CLASS%"=="" echo Test Class: %TEST_CLASS%
echo.

REM Validate environment
if not "%ENVIRONMENT%"=="dev" if not "%ENVIRONMENT%"=="staging" if not "%ENVIRONMENT%"=="prod" (
    echo [ERROR] Invalid environment '%ENVIRONMENT%'
    goto :usage
)

REM Validate test type
if not "%TEST_TYPE%"=="smoke" if not "%TEST_TYPE%"=="regression" if not "%TEST_TYPE%"=="ui" if not "%TEST_TYPE%"=="all" if not "%TEST_TYPE%"=="cucumber" (
    echo [ERROR] Invalid test type '%TEST_TYPE%'
    goto :usage
)

REM Check prerequisites
echo [INFO] Checking prerequisites...

REM Check if Appium server is running
curl -s http://localhost:4723/wd/hub/status >nul 2>&1
if %errorlevel% neq 0 (
    echo [ERROR] Appium server is not running
    echo Start Appium server with: appium --port 4723
    exit /b 1
)

REM Check if device is connected
adb devices | find "device" >nul
if %errorlevel% neq 0 (
    echo [ERROR] No devices connected
    echo Connect a device or start an emulator
    exit /b 1
)

REM Check if APK is installed
adb shell pm list packages | find "elektronicare" >nul
if %errorlevel% neq 0 (
    echo [WARNING] ElektroniCare APK not found. Installing...
    if exist "ElektroniCareBeta1-Updated.apk" (
        adb install -r ElektroniCareBeta1-Updated.apk
    ) else (
        echo [ERROR] APK file not found
        exit /b 1
    )
)

echo [INFO] Prerequisites check passed!
echo.

REM Compile project
echo [INFO] Compiling project...
mvn clean compile test-compile -q
if %errorlevel% neq 0 (
    echo [ERROR] Compilation failed
    exit /b 1
)

REM Prepare Maven command
set MVN_CMD=mvn test -Denv=%ENVIRONMENT%

REM Add test type configuration
if "%TEST_TYPE%"=="smoke" (
    set MVN_CMD=%MVN_CMD% -Dgroups=smoke
) else if "%TEST_TYPE%"=="regression" (
    set MVN_CMD=%MVN_CMD% -Dgroups=regression
) else if "%TEST_TYPE%"=="ui" (
    set MVN_CMD=%MVN_CMD% -Dgroups=ui
) else if "%TEST_TYPE%"=="cucumber" (
    set MVN_CMD=%MVN_CMD% -Dtest=TestRunner
)

REM Add specific test class if provided
if not "%TEST_CLASS%"=="" if not "%TEST_TYPE%"=="cucumber" (
    set MVN_CMD=%MVN_CMD% -Dtest=%TEST_CLASS%
)

REM Execute tests
echo [INFO] Executing tests...
echo Command: %MVN_CMD%
echo.

REM Create timestamp for this test run
for /f "tokens=2 delims==" %%a in ('wmic OS Get localdatetime /value') do set "dt=%%a"
set "TIMESTAMP=%dt:~0,8%_%dt:~8,6%"
set "LOG_FILE=test-execution-%TIMESTAMP%.log"

REM Run tests and capture output
%MVN_CMD% 2>&1 | tee %LOG_FILE%

set TEST_RESULT=%errorlevel%

echo.
echo ==================================

REM Check test results
if %TEST_RESULT% equ 0 (
    echo [INFO] ✅ Tests completed successfully!
) else (
    echo [ERROR] ❌ Tests failed!
)

REM Display report locations
echo.
echo [INFO] 📊 Test Reports:
echo TestNG Report: target\surefire-reports\index.html
if "%TEST_TYPE%"=="cucumber" (
    echo Cucumber Report: target\cucumber-reports\index.html
)
echo Screenshots: test-output\screenshots\
echo Execution Log: %LOG_FILE%

REM Display test summary
echo.
echo [INFO] 📈 Test Summary:
if exist "target\surefire-reports\testng-results.xml" (
    REM Parse XML for test results (simplified)
    echo Check target\surefire-reports\testng-results.xml for detailed results
)

REM Ask to open reports
echo.
set /p OPEN_REPORTS="Open test reports in browser? (y/n): "
if /i "%OPEN_REPORTS%"=="y" (
    start target\surefire-reports\index.html
    if "%TEST_TYPE%"=="cucumber" if exist "target\cucumber-reports\index.html" (
        start target\cucumber-reports\index.html
    )
)

echo.
echo [INFO] Happy Testing! 🚀
echo.
exit /b %TEST_RESULT%

:usage
echo.
echo Usage: %0 [environment] [test-type] [test-class]
echo.
echo Environment:
echo   dev      - Development environment (default)
echo   staging  - Staging environment
echo   prod     - Production environment
echo.
echo Test Type:
echo   smoke    - Smoke tests
echo   regression - Regression tests
echo   ui       - UI tests
echo   all      - All tests (default)
echo   cucumber - Cucumber BDD tests
echo.
echo Test Class (optional):
echo   SplashTest, LoginTest, DashboardTest, etc.
echo.
echo Examples:
echo   %0                           # Run all tests in dev environment
echo   %0 staging smoke             # Run smoke tests in staging
echo   %0 dev ui LoginTest          # Run LoginTest UI tests in dev
echo   %0 prod cucumber             # Run Cucumber tests in production
echo.
exit /b 1