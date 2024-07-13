@rem
@rem Copyright 2015 the original author or authors.
@rem
@rem Licensed under the Apache License, Version 2.0 (the "License");
@rem you may not use this file except in compliance with the License.
@rem You may obtain a copy of the License at
@rem
@rem      https://www.apache.org/licenses/LICENSE-2.0
@rem
@rem Unless required by applicable law or agreed to in writing, software
@rem distributed under the License is distributed on an "AS IS" BASIS,
@rem WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
@rem See the License for the specific language governing permissions and
@rem limitations under the License.
@rem

@if "%DEBUG%"=="" @echo off
@rem ##########################################################################
@rem
@rem  Flying-drone-in-3D-model- startup script for Windows
@rem
@rem ##########################################################################

@rem Set local scope for the variables with windows NT shell
if "%OS%"=="Windows_NT" setlocal

set DIRNAME=%~dp0
if "%DIRNAME%"=="" set DIRNAME=.
@rem This is normally unused
set APP_BASE_NAME=%~n0
set APP_HOME=%DIRNAME%..

@rem Resolve any "." and ".." in APP_HOME to make it shorter.
for %%i in ("%APP_HOME%") do set APP_HOME=%%~fi

@rem Add default JVM options here. You can also use JAVA_OPTS and FLYING_DRONE_IN_3_D_MODEL_OPTS to pass JVM options to this script.
set DEFAULT_JVM_OPTS="--module-path" "C:/Users/nirme/Downloads/openjfx-22.0.1_windows-x64_bin-sdk/javafx-sdk-22.0.1/lib" "--add-modules" "javafx.controls,javafx.fxml"

@rem Find java.exe
if defined JAVA_HOME goto findJavaFromJavaHome

set JAVA_EXE=java.exe
%JAVA_EXE% -version >NUL 2>&1
if %ERRORLEVEL% equ 0 goto execute

echo. 1>&2
echo ERROR: JAVA_HOME is not set and no 'java' command could be found in your PATH. 1>&2
echo. 1>&2
echo Please set the JAVA_HOME variable in your environment to match the 1>&2
echo location of your Java installation. 1>&2

goto fail

:findJavaFromJavaHome
set JAVA_HOME=%JAVA_HOME:"=%
set JAVA_EXE=%JAVA_HOME%/bin/java.exe

if exist "%JAVA_EXE%" goto execute

echo. 1>&2
echo ERROR: JAVA_HOME is set to an invalid directory: %JAVA_HOME% 1>&2
echo. 1>&2
echo Please set the JAVA_HOME variable in your environment to match the 1>&2
echo location of your Java installation. 1>&2

goto fail

:execute
@rem Setup the command line

set CLASSPATH=%APP_HOME%\lib\Flying-drone-in-3D-model-.jar;%APP_HOME%\lib\jme3-lwjgl-3.5.2-stable.jar;%APP_HOME%\lib\jme3-desktop-3.5.2-stable.jar;%APP_HOME%\lib\jme3-jogg-3.5.2-stable.jar;%APP_HOME%\lib\jme3-core-3.5.2-stable.jar;%APP_HOME%\lib\swingx-1.6.1.jar;%APP_HOME%\lib\javafx-swing-22.0.1-win.jar;%APP_HOME%\lib\javafx-fxml-22.0.1-win.jar;%APP_HOME%\lib\javafx-controls-22.0.1-win.jar;%APP_HOME%\lib\javafx-controls-22.0.1.jar;%APP_HOME%\lib\javafx-graphics-22.0.1-win.jar;%APP_HOME%\lib\javafx-graphics-22.0.1.jar;%APP_HOME%\lib\javafx-base-22.0.1-win.jar;%APP_HOME%\lib\javafx-base-22.0.1.jar;%APP_HOME%\lib\lwjgl-2.9.3.jar;%APP_HOME%\lib\jinput-2.0.9.jar;%APP_HOME%\lib\jinput-2.0.9-natives-all.jar;%APP_HOME%\lib\j-ogg-all-1.0.1.jar;%APP_HOME%\lib\filters-2.0.235.jar;%APP_HOME%\lib\swing-worker-1.1.jar;%APP_HOME%\lib\lwjgl-platform-2.9.3-natives-windows.jar;%APP_HOME%\lib\lwjgl-platform-2.9.3-natives-linux.jar;%APP_HOME%\lib\lwjgl-platform-2.9.3-natives-osx.jar;%APP_HOME%\lib\jmf-2.1.1e.jar


@rem Execute Flying-drone-in-3D-model-
"%JAVA_EXE%" %DEFAULT_JVM_OPTS% %JAVA_OPTS% %FLYING_DRONE_IN_3_D_MODEL_OPTS%  -classpath "%CLASSPATH%" com.example.DroneSimulation %*

:end
@rem End local scope for the variables with windows NT shell
if %ERRORLEVEL% equ 0 goto mainEnd

:fail
rem Set variable FLYING_DRONE_IN_3_D_MODEL_EXIT_CONSOLE if you need the _script_ return code instead of
rem the _cmd.exe /c_ return code!
set EXIT_CODE=%ERRORLEVEL%
if %EXIT_CODE% equ 0 set EXIT_CODE=1
if not ""=="%FLYING_DRONE_IN_3_D_MODEL_EXIT_CONSOLE%" exit %EXIT_CODE%
exit /b %EXIT_CODE%

:mainEnd
if "%OS%"=="Windows_NT" endlocal

:omega
