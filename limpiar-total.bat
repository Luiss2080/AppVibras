@echo off
echo Limpiando proyecto AppVibras...
echo.

echo [1/3] Eliminando carpeta build...
rd /s /q app\build 2>nul
rd /s /q build 2>nul

echo [2/3] Eliminando cache de Gradle...
rd /s /q .gradle 2>nul

echo [3/3] Limpieza completa con Gradle...
call gradlew clean

echo.
echo ===================================
echo Limpieza completada!
echo Ahora puedes ejecutar: gradlew assembleDebug
echo ===================================
pause

