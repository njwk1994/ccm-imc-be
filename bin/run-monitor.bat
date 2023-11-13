@echo off
echo.
echo Admin
echo.

title Admin

cd %~dp0
cd ../imc-visual/imc-monitor/target

set JAVA_OPTS=-Xms512m -Xmx1024m -XX:MetaspaceSize=128m -XX:MaxMetaspaceSize=512m

java -Dfile.encoding=utf-8 %JAVA_OPTS% -jar imc-visual-monitor.jar

cd bin
pause