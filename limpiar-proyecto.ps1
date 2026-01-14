# Script para limpiar y reconstruir el proyecto Android
Write-Host "=== Limpiando proyecto AppVibras ===" -ForegroundColor Cyan

# Eliminar carpetas de build
Write-Host "`nEliminando carpetas de build..." -ForegroundColor Yellow
if (Test-Path "app\build") {
    Remove-Item -Recurse -Force "app\build"
    Write-Host "✓ app\build eliminada" -ForegroundColor Green
}
if (Test-Path "build") {
    Remove-Item -Recurse -Force "build"
    Write-Host "✓ build eliminada" -ForegroundColor Green
}

# Limpiar con Gradle
Write-Host "`nEjecutando Gradle clean..." -ForegroundColor Yellow
.\gradlew clean --no-daemon

Write-Host "`n=== Limpieza completa! ===" -ForegroundColor Green
Write-Host "`nAhora puedes hacer 'Build > Rebuild Project' en Android Studio" -ForegroundColor Cyan

