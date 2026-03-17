@echo off
echo ========================================
echo CREATING EXCEL FILE AND RUNNING TESTS
echo ========================================

echo.
echo 1. Tạo file Excel với dữ liệu test...
mvn compile exec:java -Dexec.mainClass="utils.ExcelDataGenerator" -Dexec.classpathScope="test"

echo.
echo 2. Chạy Smoke Tests (chỉ SmokeCases sheet)...
mvn test -Dgroups=smoke

echo.
echo 3. Chạy Regression Tests (tất cả 3 sheets)...
mvn test -Dgroups=regression

echo.
echo 4. Tạo TestNG HTML Report...
echo Report được tạo tại: test-output/index.html

pause