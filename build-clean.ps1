# Script para limpiar y reconstruir AppVibras
Write-Host "========================================" -ForegroundColor Cyan
Write-Host "   Limpiando y Reconstruyendo AppVibras" -ForegroundColor Cyan
Write-Host "========================================" -ForegroundColor Cyan
Write-Host ""

# Limpiar directorios de build
Write-Host "Limpiando directorios de build..." -ForegroundColor Yellow
if (Test-Path ".\app\build") {
    Remove-Item -Recurse -Force ".\app\build"
    Write-Host "✓ Eliminado app\build" -ForegroundColor Green
}
if (Test-Path ".\build") {
    Remove-Item -Recurse -Force ".\build"
    Write-Host "✓ Eliminado build" -ForegroundColor Green
}
if (Test-Path ".\.gradle\caches") {
    Remove-Item -Recurse -Force ".\.gradle\caches"
    Write-Host "✓ Eliminado .gradle\caches" -ForegroundColor Green
}
Write-Host ""

# Verificar que gradle existe
if (-not (Test-Path ".\gradlew")) {
    Write-Host "✗ No se encontró gradlew" -ForegroundColor Red
    Write-Host "Por favor, usa Android Studio para construir el proyecto" -ForegroundColor Yellow
    pause
    exit
}

Write-Host "Construyendo proyecto..." -ForegroundColor Yellow
Write-Host "Esto puede tomar varios minutos..." -ForegroundColor Gray
Write-Host ""

# Intentar construir usando gradle wrapper
$env:GRADLE_OPTS = "-Dorg.gradle.daemon=true"
bash gradlew clean assembleDebug

if ($LASTEXITCODE -eq 0) {
    Write-Host ""
    Write-Host "========================================" -ForegroundColor Green
    Write-Host "   BUILD EXITOSO" -ForegroundColor Green
    Write-Host "========================================" -ForegroundColor Green
    Write-Host ""
    Write-Host "APK generado en:" -ForegroundColor Cyan
    Write-Host "app\build\outputs\apk\debug\app-debug.apk" -ForegroundColor White
    Write-Host ""
} else {
    Write-Host ""
    Write-Host "========================================" -ForegroundColor Red
    Write-Host "   BUILD FALLIDO" -ForegroundColor Red
    Write-Host "========================================" -ForegroundColor Red
    Write-Host ""
    Write-Host "Usa Android Studio para ver los errores detallados:" -ForegroundColor Yellow
    Write-Host "1. Abre el proyecto en Android Studio" -ForegroundColor White
    Write-Host "2. Build > Clean Project" -ForegroundColor White
    Write-Host "3. Build > Rebuild Project" -ForegroundColor White
    Write-Host ""
}

pause

