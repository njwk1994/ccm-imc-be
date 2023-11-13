@echo off
echo.
echo Modules-general
echo.

title General

cd %~dp0
cd ../imc-modules/imc-general/target

set JAVA_OPTS=-Xms512m -Xmx1024m -XX:MetaspaceSize=128m -XX:MaxMetaspaceSize=512m

java -Dfile.encoding=utf-8 %JAVA_OPTS% -jar imc-modules-general.jar

cd bin
pause