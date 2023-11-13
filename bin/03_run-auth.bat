@echo off
echo.
echo Auth
echo.

title Auth

cd %~dp0
cd ../imc-auth/target

set JAVA_OPTS=-Xms512m -Xmx1024m -XX:MetaspaceSize=128m -XX:MaxMetaspaceSize=512m

java -Dfile.encoding=utf-8 %JAVA_OPTS% -jar imc-auth.jar

cd bin
pause