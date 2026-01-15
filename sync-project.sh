#!/bin/bash
# Script para sincronizar y reconstruir el proyecto

echo "🔧 Sincronizando proyecto AppVibras..."
echo ""

echo "Paso 1: Deteniendo Gradle daemon..."
./gradlew --stop

echo ""
echo "Paso 2: Limpiando proyecto..."
./gradlew clean

echo ""
echo "Paso 3: Compilando..."
./gradlew assembleDebug

echo ""
echo "✅ Sincronización completada!"
echo "Ahora en Android Studio:"
echo "  1. File → Sync Project with Gradle Files"
echo "  2. Build → Rebuild Project"

