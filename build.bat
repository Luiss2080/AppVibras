@echo off
echo ========================================
echo    Construyendo AppVibras
echo ========================================
echo.

echo Limpiando proyecto...
call gradle clean
echo.

echo Construyendo APK Debug...
call gradle assembleDebug
echo.

if %ERRORLEVEL% EQU 0 (
    echo ========================================
    echo    BUILD EXITOSO
    echo ========================================
    echo.
    echo APK generado en: app\build\outputs\apk\debug\
    echo.
) else (
    echo ========================================
    echo    BUILD FALLIDO
    echo ========================================
    echo.
    echo Revisa los errores arriba
    echo.
)

pause

