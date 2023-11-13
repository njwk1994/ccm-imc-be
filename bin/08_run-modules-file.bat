@echo off
echo.
echo Modules-File
echo.

title File

cd %~dp0
cd ../imc-modules/imc-file/target

set JAVA_OPTS=-Xms512m -Xmx1024m -XX:MetaspaceSize=128m -XX:MaxMetaspaceSize=512m

java -Dfile.encoding=utf-8 %JAVA_OPTS% -jar imc-modules-file.jar

cd bin
pause