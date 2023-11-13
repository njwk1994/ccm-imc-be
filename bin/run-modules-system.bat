@echo off
echo.
echo Modules-System
echo.

title System

cd %~dp0
cd ../imc-modules/imc-system/target

set JAVA_OPTS=-Xms512m -Xmx1024m -XX:MetaspaceSize=128m -XX:MaxMetaspaceSize=512m

java -Dfile.encoding=utf-8 %JAVA_OPTS% -jar imc-modules-system.jar

cd bin
pause