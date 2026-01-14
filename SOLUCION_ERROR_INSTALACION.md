# 🔧 SOLUCIÓN - Error de Instalación "Can't find service: package"

## ❌ Error que estás viendo:

```
Error running 'app'
The application could not be installed: Installation failed due to:
'Error code: 'UNKNOWN', message='Unknown failure: 'cmd: Can't find service: package''
```

## 🔍 Causa del Problema

Este error ocurre cuando:
1. El emulador tiene problemas con el servicio Package Manager
2. ADB (Android Debug Bridge) está en un estado corrupto
3. El emulador no se inició correctamente

## ✅ SOLUCIONES (prueba en orden)

---

### 🚀 SOLUCIÓN 1: Reiniciar Emulador (MÁS RÁPIDA)

#### Paso 1: Cerrar el emulador actual
- Click derecho en la ventana del emulador
- Selecciona "Close" o cierra la ventana

#### Paso 2: Reiniciar el emulador desde Android Studio
1. En Android Studio, arriba: **Tools** → **Device Manager**
2. Busca "Medium Phone API 36.1" (o el que estabas usando)
3. Click en el botón ▶ verde para iniciarlo
4. Espera 1-2 minutos a que cargue completamente

#### Paso 3: Volver a ejecutar la app
```
Run → Run 'app' (Shift + F10)
```

---

### 🔧 SOLUCIÓN 2: Reiniciar ADB (SI SOLUCIÓN 1 NO FUNCIONA)

#### Desde Android Studio:

1. **Tools** → **Troubleshoot Device Connections**
2. Click en "**Restart ADB Server**"
3. Espera 10 segundos
4. Vuelve a ejecutar: **Run → Run 'app'**

#### O desde Terminal en Android Studio:

```bash
adb kill-server
adb start-server
adb devices
```

Deberías ver algo como:
```
List of devices attached
emulator-5554    device
```

---

### 🔄 SOLUCIÓN 3: Cold Boot del Emulador (SI LAS ANTERIORES NO FUNCIONAN)

#### Paso 1: Abrir Device Manager
```
Tools → Device Manager
```

#### Paso 2: Cold Boot
1. Click en el menú **⋮** (3 puntos) del emulador "Medium Phone API 36.1"
2. Selecciona "**Cold Boot Now**"
3. Espera 2-3 minutos a que inicie completamente

#### Paso 3: Verificar que está listo
- El emulador debe mostrar la pantalla de inicio
- No debe mostrar "Android" animándose indefinidamente

#### Paso 4: Ejecutar la app
```
Run → Run 'app'
```

---

### 🆕 SOLUCIÓN 4: Crear Nuevo Emulador (ÚLTIMA OPCIÓN)

Si ninguna solución anterior funciona:

#### Paso 1: Crear nuevo emulador
1. **Tools** → **Device Manager**
2. Click en "**Create Device**"
3. Selecciona "**Pixel 7 Pro**" (recomendado)
4. Click "**Next**"
5. Selecciona "**Android 14.0 (API 36)**" o superior
6. Si no está descargado, click en "Download" junto al nombre
7. Click "**Next**"
8. Nombre: "Pixel7Pro_API36"
9. Click "**Finish**"

#### Paso 2: Iniciar el nuevo emulador
1. En Device Manager, busca "Pixel7Pro_API36"
2. Click en ▶ para iniciarlo
3. Espera 2-3 minutos

#### Paso 3: Seleccionar como destino
1. En Android Studio, arriba al lado del botón Run
2. En el dropdown, selecciona "Pixel7Pro_API36"
3. Click en Run ▶

---

### 📱 SOLUCIÓN 5: Usar Dispositivo Físico (ALTERNATIVA)

Si tienes un teléfono Android:

#### Paso 1: Activar Depuración USB
1. En tu teléfono: **Configuración** → **Acerca del teléfono**
2. Toca 7 veces en "**Número de compilación**"
3. Vuelve y entra a "**Opciones de desarrollador**"
4. Activa "**Depuración USB**"

#### Paso 2: Conectar por USB
1. Conecta el teléfono a la PC por USB
2. En el teléfono aparecerá: "¿Permitir depuración USB?"
3. Marca "**Permitir siempre desde este equipo**"
4. Toca "**Permitir**"

#### Paso 3: Verificar conexión
En Android Studio:
- Arriba al lado del botón Run debe aparecer el modelo de tu teléfono
- Selecciónalo en el dropdown

#### Paso 4: Ejecutar
```
Run → Run 'app'
```

---

## 🎯 SOLUCIÓN RÁPIDA RECOMENDADA

**La más efectiva en tu caso:**

1. **Cierra el emulador** Medium Phone API 36.1
2. Espera 10 segundos
3. En Android Studio: **Tools** → **Device Manager**
4. Click en ▶ verde junto a "Medium Phone API 36.1"
5. Espera a que cargue completamente (2 minutos)
6. **Run** → **Run 'app'**

---

## ✅ Verificación

Después de aplicar cualquier solución, deberías ver:

```
✅ Installing APK...
✅ APK installed in XX ms
✅ Launching app...
✅ App launched successfully
```

Y la app se abrirá en el emulador.

---

## 🔍 Diagnóstico Adicional

Si el error persiste después de TODAS las soluciones:

### Ver logs del emulador:
```
Tools → Logcat
```
Busca mensajes de error en rojo.

### Verificar espacio en disco:
- El emulador necesita al menos 2-3 GB libres
- Verifica: Mi PC → Disco C: → Propiedades

### Actualizar Android Studio:
```
Help → Check for Updates
```

---

## 📋 Checklist de Solución

Marca lo que ya probaste:

- [ ] Cerré y reinicié el emulador
- [ ] El emulador cargó completamente (pantalla de inicio visible)
- [ ] Reinicié ADB server
- [ ] Hice Cold Boot del emulador
- [ ] Esperé 2-3 minutos después de iniciar el emulador
- [ ] Verifiqué que haya espacio en disco (>2GB)
- [ ] Probé con un dispositivo físico
- [ ] Creé un nuevo emulador

---

## 🚨 Error Específico de tu Caso

El error indica que el **Package Manager Service** del emulador no está respondiendo.

**Causa más común:** El emulador no terminó de inicializar completamente.

**Solución más efectiva:**
1. Cierra el emulador
2. Espera 10 segundos
3. Reinícialo con "Cold Boot Now"
4. Espera a ver la pantalla de inicio completamente
5. Vuelve a ejecutar la app

---

**Última actualización:** 2026-01-14  
**Error:** Unknown failure: cmd: Can't find service: package  
**Solución recomendada:** Cold Boot del emulador  
**Tiempo estimado:** 3-5 minutos

