@echo off
setlocal

set "MAVEN_VERSION=3.9.5"
set "MAVEN_DIR=%~dp0.mvn\maven"
set "MAVEN_HOME=%MAVEN_DIR%\apache-maven-%MAVEN_VERSION%"
set "MAVEN_ZIP=%MAVEN_DIR%\maven.zip"
set "MAVEN_URL=https://archive.apache.org/dist/maven/maven-3/%MAVEN_VERSION%/binaries/apache-maven-%MAVEN_VERSION%-bin.zip"

echo =====================================================
echo  SafeRoad iRAD - Java Spring Boot Launcher
echo =====================================================
echo.

:: Check Java
java -version >nul 2>&1
IF %ERRORLEVEL% NEQ 0 (
    echo [ERROR] Java is not installed or not in PATH.
    echo Please install JDK 17 from: https://www.oracle.com/java/technologies/downloads/
    pause
    exit /b 1
)

echo [OK] Java found.
echo.

:: Check if Maven already downloaded
IF EXIST "%MAVEN_HOME%\bin\mvn.cmd" (
    echo [OK] Maven already present. Skipping download.
    goto :runProject
)

:: Download Maven
echo [1/2] Downloading Maven %MAVEN_VERSION% (first time only, ~10MB)...
IF NOT EXIST "%MAVEN_DIR%" mkdir "%MAVEN_DIR%"

powershell -Command "& { [Net.ServicePointManager]::SecurityProtocol = [Net.SecurityProtocolType]::Tls12; (New-Object Net.WebClient).DownloadFile('%MAVEN_URL%', '%MAVEN_ZIP%') }"

IF %ERRORLEVEL% NEQ 0 (
    echo [ERROR] Failed to download Maven. Check your internet connection.
    pause
    exit /b 1
)

echo [2/2] Extracting Maven...
powershell -Command "Expand-Archive -Path '%MAVEN_ZIP%' -DestinationPath '%MAVEN_DIR%' -Force"
del "%MAVEN_ZIP%"

echo.
echo [OK] Maven ready!

:runProject
echo.
echo Starting SafeRoad Spring Boot application...
echo Open your browser at: http://localhost:8080
echo Login: admin / admin
echo (Press Ctrl+C to stop the server)
echo.

"%MAVEN_HOME%\bin\mvn.cmd" spring-boot:run

pause
