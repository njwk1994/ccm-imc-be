@echo off
echo.
echo Modules-administrator
echo.

title administrator

cd %~dp0
cd ../imc-modules/imc-administrator/target

set JAVA_OPTS=-Xms512m -Xmx1024m -XX:MetaspaceSize=128m -XX:MaxMetaspaceSize=512m

java -Dfile.encoding=utf-8 %JAVA_OPTS% -jar imc-modules-administrator.jar

cd bin
pause