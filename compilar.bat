@echo off
echo ==========================================
echo LIMPIEZA Y COMPILACION - AppVibras
echo ==========================================
echo.
echo Este script limpia y compila el proyecto AppVibras.
echo.
echo NOTA IMPORTANTE:
echo Si experimentas errores relacionados con Java 25/Kotlin,
echo por favor compila el proyecto desde Android Studio.
echo.
pause

echo.
echo ==========================================
echo [1/3] Deteniendo daemons de Gradle...
echo ==========================================
call gradlew.bat --stop

echo.
echo ==========================================
echo [2/3] Limpiando proyecto...
echo ==========================================
call gradlew.bat clean --warning-mode all

echo.
echo ==========================================
echo [3/3] Compilando proyecto...
echo ==========================================
call gradlew.bat assembleDebug --warning-mode all

echo.
echo ==========================================
if %ERRORLEVEL% EQU 0 (
    echo BUILD EXITOSO!
    echo El APK se encuentra en: app\build\outputs\apk\debug\
) else (
    echo BUILD FALLIDO!
    echo.
    echo Posibles soluciones:
    echo 1. Abre el proyecto en Android Studio y compila desde ahi
    echo 2. Verifica que Android Studio este instalado correctamente
    echo 3. Revisa el archivo CORRECCIONES_Y_MEJORAS.md en la carpeta docs
)
echo ==========================================
echo.
pause

