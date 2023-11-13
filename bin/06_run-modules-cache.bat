@echo off
echo.
echo Modules-cache
echo.

title Cache

cd %~dp0
cd ../imc-modules/imc-cache/target

set JAVA_OPTS=-Xms512m -Xmx1024m -XX:MetaspaceSize=128m -XX:MaxMetaspaceSize=512m

java -Dfile.encoding=utf-8 %JAVA_OPTS% -jar imc-modules-cache.jar

cd bin
pause