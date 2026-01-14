# Script para limpiar y reconstruir el proyecto Android
# Este script resuelve problemas con archivos R.java no generados correctamente

Write-Host "=== Limpieza Completa del Proyecto ===" -ForegroundColor Cyan

# 1. Eliminar carpetas de build
Write-Host "`n[1/5] Eliminando carpetas build..." -ForegroundColor Yellow
Remove-Item -Path "app\build" -Recurse -Force -ErrorAction SilentlyContinue
Remove-Item -Path "build" -Recurse -Force -ErrorAction SilentlyContinue
Write-Host "✓ Carpetas build eliminadas" -ForegroundColor Green

# 2. Eliminar caché de Gradle
Write-Host "`n[2/5] Eliminando caché de Gradle..." -ForegroundColor Yellow
Remove-Item -Path ".gradle" -Recurse -Force -ErrorAction SilentlyContinue
Write-Host "✓ Caché de Gradle eliminada" -ForegroundColor Green

# 3. Limpiar con Gradle
Write-Host "`n[3/5] Ejecutando gradle clean..." -ForegroundColor Yellow
& .\gradlew clean
Write-Host "✓ Gradle clean completado" -ForegroundColor Green

# 4. Generar archivos R.java
Write-Host "`n[4/5] Generando archivos R.java..." -ForegroundColor Yellow
& .\gradlew :app:generateDebugResources
Write-Host "✓ Recursos generados" -ForegroundColor Green

# 5. Compilar proyecto
Write-Host "`n[5/5] Compilando proyecto..." -ForegroundColor Yellow
& .\gradlew assembleDebug
Write-Host "✓ Proyecto compilado" -ForegroundColor Green

Write-Host "`n=== Proceso Completado ===" -ForegroundColor Cyan
Write-Host "Ahora debes:" -ForegroundColor White
Write-Host "1. Cerrar Android Studio" -ForegroundColor Yellow
Write-Host "2. Abrir Android Studio nuevamente" -ForegroundColor Yellow
Write-Host "3. Esperar a que termine la indexación" -ForegroundColor Yellow
Write-Host "4. Build > Rebuild Project" -ForegroundColor Yellow

