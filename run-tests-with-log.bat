@echo off
REM ElektroniCare Appium Test Execution with Logging (Windows)
REM This script runs the Appium tests and saves all output to log files

REM Create logs directory if it doesn't exist
if not exist logs mkdir logs

REM Get current timestamp for log file naming
for /f "tokens=2 delims==" %%a in ('wmic OS Get localdatetime /value') do set "dt=%%a"
set "YY=%dt:~2,2%" & set "YYYY=%dt:~0,4%" & set "MM=%dt:~4,2%" & set "DD=%dt:~6,2%"
set "HH=%dt:~8,2%" & set "Min=%dt:~10,2%" & set "Sec=%dt:~12,2%"
set "TIMESTAMP=%YYYY%%MM%%DD%_%HH%%Min%%Sec%"

echo Starting ElektroniCare Appium Test Execution...
echo Timestamp: %TIMESTAMP%
echo Logs will be saved to: logs\test_execution_%TIMESTAMP%.log

REM Create log file with header
echo === ElektroniCare Appium Test Execution Started at %date% %time% === > logs\test_execution_%TIMESTAMP%.log
echo Test Environment: %OS% %PROCESSOR_ARCHITECTURE% >> logs\test_execution_%TIMESTAMP%.log
java -version 2>> logs\test_execution_%TIMESTAMP%.log
mvn -version 2>> logs\test_execution_%TIMESTAMP%.log
echo ======================================== >> logs\test_execution_%TIMESTAMP%.log
echo. >> logs\test_execution_%TIMESTAMP%.log

REM Run Maven test with output logging
mvn clean test -Dtest.output.verbose=true 2>&1 | tee logs\test_execution_%TIMESTAMP%.log

REM Add completion timestamp
echo. >> logs\test_execution_%TIMESTAMP%.log
echo === Test Execution Completed at %date% %time% === >> logs\test_execution_%TIMESTAMP%.log

echo Test execution completed. Check logs\test_execution_%TIMESTAMP%.log for detailed results.

REM Show summary
echo.
echo === EXECUTION SUMMARY ===
echo Log file: logs\test_execution_%TIMESTAMP%.log
dir logs\test_execution_%TIMESTAMP%.log
echo ==========================

pause