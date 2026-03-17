@echo off
echo Running Login Tests only...
mvn test -Dtest=LoginTest
pause

echo Running Cart Tests only...
mvn test -Dtest=CartTest
pause

echo Running specific test method...
mvn test -Dtest=LoginTest#testValidLogin
pause