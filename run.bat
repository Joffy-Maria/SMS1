@echo off
REM Build fat JAR and run
gradle clean shadowJar
java -jar build\libs\SupermarketManagementSystem-all.jar