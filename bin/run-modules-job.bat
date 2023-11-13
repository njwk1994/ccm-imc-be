@echo off
echo.
echo Modules-Job
echo.

title Job

cd %~dp0
cd ../imc-modules/imc-job/target

set JAVA_OPTS=-Xms512m -Xmx1024m -XX:MetaspaceSize=128m -XX:MaxMetaspaceSize=512m

java -Dfile.encoding=utf-8 %JAVA_OPTS% -jar imc-modules-job.jar

cd bin
pause