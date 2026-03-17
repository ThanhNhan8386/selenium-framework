@echo off
echo ========================================
echo Testing Selenium Framework
echo ========================================

echo.
echo 1. Checking for Thread.sleep in BasePage...
findstr /r "Thread\.sleep" src\main\java\base\*.java
if %errorlevel% equ 0 (
    echo ERROR: Found Thread.sleep in BasePage!
    exit /b 1
) else (
    echo PASS: No Thread.sleep found in BasePage
)

echo.
echo 2. Creating screenshots directory...
if not exist "target\screenshots" mkdir target\screenshots

echo.
echo 3. Running tests with Maven...
mvn clean test

echo.
echo 4. Checking screenshots were created...
if exist "target\screenshots\*.png" (
    echo PASS: Screenshots were created
    dir target\screenshots\*.png
) else (
    echo WARNING: No screenshots found
)

echo.
echo ========================================
echo Framework test completed!
echo ========================================
pause