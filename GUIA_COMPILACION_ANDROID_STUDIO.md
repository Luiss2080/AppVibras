# 🚀 GUÍA RÁPIDA - Compilar e Instalar AppVibras

## ⚠️ PROBLEMA DETECTADO

**Gradle no puede ejecutarse desde línea de comandos** debido a incompatibilidad con Java 25.0.1

```
Error: "What went wrong: 25.0.1"
```

## ✅ SOLUCIÓN: Compilar desde Android Studio

No te preocupes, es muy simple. Sigue estos pasos:

---

## 📋 PASOS A SEGUIR (5 minutos)

### Paso 1: Abrir Android Studio
- Abre Android Studio
- El proyecto AppVibras debe estar abierto

### Paso 2: Limpiar el Proyecto
```
En la barra de menú superior:
Build → Clean Project
```
- Verás un mensaje en la parte inferior: "Gradle Build Running..."
- Espera 10-30 segundos hasta que diga: "BUILD SUCCESSFUL"

### Paso 3: Reconstruir el Proyecto  
```
En la barra de menú superior:
Build → Rebuild Project
```
- Verás progreso en la parte inferior
- Espera 1-2 minutos hasta que diga: "BUILD SUCCESSFUL"
- **MUY IMPORTANTE**: Espera a que termine completamente

### Paso 4: Conectar Dispositivo/Emulador

**Opción A - Emulador:**
- En Android Studio, arriba a la derecha, haz click en el ícono del teléfono
- Selecciona un emulador (Medium Phone, Pixel 7 Pro, etc.)
- Click en el botón ▶ (Play) para iniciarlo
- Espera 1-2 minutos a que inicie

**Opción B - Dispositivo físico:**
- Conecta tu teléfono por USB
- Activa "Depuración USB" en el teléfono:
  - Configuración → Acerca del teléfono → Toca 7 veces en "Número de compilación"
  - Vuelve → Opciones de desarrollador → Activa "Depuración USB"
- Autoriza la conexión en el teléfono

### Paso 5: Desinstalar App Anterior

**Desde el dispositivo/emulador:**
- Busca el ícono de "AppVibras"
- Mantén presionado
- Arrastra a "Desinstalar" o "Información de la app" → Desinstalar

### Paso 6: Instalar Nueva Versión
```
En Android Studio, arriba a la derecha:
Run → Run 'app'

O simplemente presiona: Shift + F10
```

Verás:
```
1. "Gradle Build Running..." (30 segundos)
2. "Installing APK..." (10 segundos)
3. "Launching app..." (5 segundos)
```

### Paso 7: Verificar que Funciona ✅

La app se abrirá automáticamente:

1. **Login:**
   - Usuario: `admin`
   - Contraseña: `admin123`
   - Click en "Ingresar"

2. **Verás el Menú Principal** con 6 botones

3. **Presiona "Categorías":**
   - ✅ Debe aparecer un mensaje: "Navegando a Categorías..."
   - ✅ La pantalla debe cambiar a la lista de categorías

4. **¡LISTO!** La navegación está funcionando

---

## 🔍 Verificación Visual

### ✅ CORRECTO (lo que debes ver):
```
[Presionas "Categorías"]
    ↓
[Aparece mensaje Toast: "Navegando a Categorías..."]
    ↓
[La pantalla cambia → Lista de Categorías]
    ↓
[Puedes regresar con ← o botón Atrás]
```

### ❌ INCORRECTO (si ves esto, no se instaló correctamente):
```
[Presionas "Categorías"]
    ↓
[No pasa nada]
    ↓
[Sigue en el menú principal]
```

**Si pasa esto:** Repite desde el Paso 5 (desinstalar e instalar de nuevo)

---

## 📱 Probar Todos los Botones

Después de verificar que "Categorías" funciona, prueba:

| Botón | Mensaje que Aparece | Pantalla |
|-------|---------------------|----------|
| **Categorías** | "Navegando a Categorías..." | Lista de categorías |
| **Productos** | "Navegando a Productos..." | Lista de productos |
| **Entradas** | "Navegando a Compras..." | Registro de compras |
| **Salidas** | "Navegando a Ventas..." | Registro de ventas |
| **Reportes** | "Navegando a Clientes..." | Lista de clientes |
| **Cerrar Sesión** | "Cerrando sesión..." | Pantalla de login |

---

## ⚠️ Problemas Comunes

### Problema 1: "BUILD FAILED" al compilar

**Causa:** Archivos de Gradle corruptos

**Solución:**
1. File → Invalidate Caches / Restart
2. Click en "Invalidate and Restart"
3. Espera a que Android Studio reinicie
4. Repite desde el Paso 2

### Problema 2: "App not installed"

**Causa:** Versión anterior no se desinstaló completamente

**Solución:**
1. Desinstala manualmente desde el dispositivo
2. Verifica que ya NO esté en la lista de apps
3. Repite el Paso 6

### Problema 3: No aparece el emulador/dispositivo

**Causa:** Device Manager no muestra dispositivos

**Solución:**
1. En Android Studio: Tools → Device Manager
2. Crea un nuevo dispositivo virtual o verifica la conexión USB

### Problema 4: La app se cierra al abrirse

**Causa:** Error en tiempo de ejecución

**Solución:**
1. Abre la pestaña "Logcat" en Android Studio (abajo)
2. Busca el error en rojo
3. Copia el mensaje de error completo

---

## 🎯 Resumen en 3 Pasos

Si solo quieres lo esencial:

1. **Build → Rebuild Project** (espera a que termine)
2. **Desinstala** la app del dispositivo
3. **Run → Run 'app'** (o Shift+F10)

---

## 📞 Ayuda Adicional

Si después de seguir todos los pasos NO funciona:

1. Verifica que el build fue exitoso (debe decir "BUILD SUCCESSFUL")
2. Confirma que desinstalaste la app anterior
3. Mira si hay errores en la pestaña "Build" de Android Studio
4. Revisa Logcat para ver errores en tiempo de ejecución

---

**Última actualización:** 2026-01-14  
**Motivo:** Incompatibilidad de Gradle con Java 25.0.1  
**Solución:** Usar Android Studio en lugar de línea de comandos  
**Estado:** ✅ Código corregido - Compilar desde Android Studio

