@echo off
echo ==========================================
echo Limpiando proyecto AppVibras...
echo ==========================================
call gradlew.bat clean --no-daemon --warning-mode all

echo.
echo ==========================================
echo Compilando proyecto AppVibras...
echo ==========================================
call gradlew.bat assembleDebug --no-daemon --warning-mode all

echo.
echo ==========================================
echo Build completado!
echo ==========================================
pause

