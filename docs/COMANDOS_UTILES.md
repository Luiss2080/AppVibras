# 🛠️ COMANDOS ÚTILES - AppVibras

## 📱 Compilación y Build

### Limpiar proyecto
```batch
cd C:\Users\LuissxD\AndroidStudioProjects\AppVibras
.\gradlew.bat clean
```

### Compilar Debug APK
```batch
.\gradlew.bat assembleDebug
```

### Compilar Release APK
```batch
.\gradlew.bat assembleRelease
```

### Compilar y ejecutar directamente
```batch
.\gradlew.bat installDebug
```

### Detener daemons de Gradle
```batch
.\gradlew.bat --stop
```

### Ver dependencias del proyecto
```batch
.\gradlew.bat dependencies
```

## 🔍 Debugging

### Ver Logcat (desde terminal)
```batch
adb logcat | findstr AppVibras
```

### Limpiar Logcat
```batch
adb logcat -c
```

### Ver dispositivos conectados
```batch
adb devices
```

### Desinstalar app del dispositivo
```batch
adb uninstall com.example.appvibras
```

### Instalar APK manualmente
```batch
adb install app\build\outputs\apk\debug\app-debug.apk
```

## 📊 Base de Datos

### Ubicación de la BD en el dispositivo
```
/data/data/com.example.appvibras/databases/AppVibrasDB
```

### Exportar BD desde dispositivo
```batch
adb pull /data/data/com.example.appvibras/databases/AppVibrasDB C:\temp\
```

### Ver tablas de la BD (requiere root o emulador)
```batch
adb shell
run-as com.example.appvibras
cd databases
sqlite3 AppVibrasDB
.tables
.schema productos
SELECT * FROM productos;
.exit
```

## 🧹 Limpieza

### Limpiar caché de Gradle
```batch
.\gradlew.bat cleanBuildCache
```

### Eliminar carpeta build
```batch
rmdir /s /q app\build
rmdir /s /q build
```

### Invalidar caché (desde Android Studio)
```
File → Invalidate Caches → Invalidate and Restart
```

## 📦 Generación de APK

### Ubicación del APK Debug
```
app\build\outputs\apk\debug\app-debug.apk
```

### Ubicación del APK Release
```
app\build\outputs\apk\release\app-release.apk
```

## 🔧 Gradle

### Ver versión de Gradle
```batch
.\gradlew.bat --version
```

### Actualizar wrapper de Gradle
```batch
.\gradlew.bat wrapper --gradle-version=8.13
```

### Verificar integridad del proyecto
```batch
.\gradlew.bat check
```

### Build completo con todos los checks
```batch
.\gradlew.bat build
```

## 🚀 Android Studio

### Atajos de teclado útiles

| Acción | Atajo |
|--------|-------|
| Compilar proyecto | Ctrl + F9 |
| Ejecutar app | Shift + F10 |
| Debug app | Shift + F9 |
| Abrir clase | Ctrl + N |
| Abrir archivo | Ctrl + Shift + N |
| Buscar en proyecto | Ctrl + Shift + F |
| Refactorizar | Ctrl + Alt + Shift + T |
| Formatear código | Ctrl + Alt + L |
| Optimizar imports | Ctrl + Alt + O |

## 📝 Git (Control de versiones)

### Inicializar repositorio
```batch
git init
git add .
git commit -m "Proyecto AppVibras inicial"
```

### Crear .gitignore para Android
```gitignore
# Build
*.iml
.gradle
/local.properties
/.idea/
.DS_Store
/build
/captures
.externalNativeBuild
.cxx

# Android Studio
*.apk
*.ap_
*.aab
bin/
gen/
out/

# Gradle
.gradle/
build/
*/build/

# Local configuration
local.properties
```

## 🐛 Solución de Problemas Comunes

### Error: "SDK location not found"
```batch
# Crear archivo local.properties con:
sdk.dir=C\:\\Users\\[TU_USUARIO]\\AppData\\Local\\Android\\Sdk
```

### Error: "Daemon will be stopped"
```batch
.\gradlew.bat --stop
.\gradlew.bat clean
.\gradlew.bat assembleDebug
```

### Error: "Could not resolve dependencies"
```batch
# Limpiar caché
.\gradlew.bat clean --refresh-dependencies
```

### Error: "Failed to apply plugin"
```batch
# Verificar gradle.properties
# Verificar build.gradle.kts
# Invalidar cachés en Android Studio
```

## 📊 Análisis de APK

### Ver tamaño del APK
```batch
dir app\build\outputs\apk\debug\app-debug.apk
```

### Analizar APK en Android Studio
```
Build → Analyze APK → Seleccionar app-debug.apk
```

## 🔄 Actualización de Dependencias

### Ver dependencias desactualizadas
```batch
.\gradlew.bat dependencyUpdates
```

### Actualizar versiones en libs.versions.toml
```toml
[versions]
compileSdk = "36"
minSdk = "24"
targetSdk = "36"
```

## 💾 Backup del Proyecto

### Crear backup completo
```batch
xcopy C:\Users\LuissxD\AndroidStudioProjects\AppVibras C:\Backups\AppVibras_%date:~-4,4%%date:~-7,2%%date:~-10,2%\ /E /I /H
```

### Excluir carpetas build del backup
```batch
xcopy C:\Users\LuissxD\AndroidStudioProjects\AppVibras C:\Backups\AppVibras\ /E /I /H /EXCLUDE:exclude.txt
# Crear exclude.txt con: build\
```

## 🌐 Comandos ADB Útiles

### Reiniciar dispositivo
```batch
adb reboot
```

### Tomar screenshot
```batch
adb shell screencap /sdcard/screen.png
adb pull /sdcard/screen.png
```

### Grabar pantalla (video)
```batch
adb shell screenrecord /sdcard/demo.mp4
# Detener con Ctrl+C
adb pull /sdcard/demo.mp4
```

### Ver información del dispositivo
```batch
adb shell getprop ro.build.version.release
adb shell getprop ro.product.model
```

## 📈 Performance

### Perfilar app
```
Run → Profile 'app'
```

### Ver uso de memoria
```batch
adb shell dumpsys meminfo com.example.appvibras
```

### Ver uso de CPU
```batch
adb shell top | findstr appvibras
```

---

**Nota:** Reemplaza `[TU_USUARIO]` con tu nombre de usuario de Windows.

**Última actualización:** 14/01/2026

