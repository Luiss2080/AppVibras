# 🔴 ERROR: Cannot resolve method 'obtenerPorId'

## ⚡ SOLUCIÓN RÁPIDA (EN ANDROID STUDIO)

### Paso 1: Sincronizar con Gradle
```
File → Sync Project with Gradle Files
```
**Atajo:** `Ctrl + Shift + O` (Windows/Linux) o `Cmd + Shift + O` (Mac)

⏱️ Espera 10-30 segundos a que termine la sincronización.

---

### Paso 2: Invalidar Cachés
```
File → Invalidate Caches / Restart...
```

Selecciona: **"Invalidate and Restart"**

⏱️ Android Studio se reiniciará (toma 1-2 minutos)

---

### Paso 3: Verificar
Después del reinicio, verifica que el error desapareció:
- Abre `ClienteDetalleActivity.java`
- La línea 51 NO debe tener error rojo
- Si aún hay error, ve al Paso 4

---

### Paso 4: Rebuild Project (si aún hay error)
```
Build → Rebuild Project
```
**Atajo:** `Ctrl + Shift + F9` (Windows/Linux)

---

## 📋 VERIFICACIÓN MANUAL

### El método SÍ existe en el código:

**Archivo:** `GestorClientes.java` (línea 32-34)
```java
public Cliente obtenerPorId(int id) {
    return db.clienteDao().obtenerPorId(id);
}
```

**Archivo:** `ClienteDao.java`
```java
@Query("SELECT * FROM clientes WHERE id = :id")
Cliente obtenerPorId(int id);
```

---

## 🎯 ¿POR QUÉ PASA ESTO?

Android Studio mantiene un **índice** de todas las clases y métodos.
Cuando agregamos código nuevo, a veces el índice no se actualiza automáticamente.

### Solución = Forzar actualización del índice

---

## ⚡ SOLUCIÓN ALTERNATIVA (TERMINAL)

Si prefieres usar la terminal de Android Studio:

```bash
# 1. Detener Gradle
./gradlew --stop

# 2. Limpiar
./gradlew clean

# 3. Compilar
./gradlew assembleDebug
```

Luego en Android Studio:
```
File → Sync Project with Gradle Files
```

---

## ✅ CONFIRMACIÓN

Sabrás que está resuelto cuando:

1. ✅ NO hay líneas rojas en `ClienteDetalleActivity.java`
2. ✅ Al hacer hover sobre `obtenerPorId()` se muestra la documentación
3. ✅ El Build Output NO muestra errores
4. ✅ Puedes compilar sin errores

---

## 🆘 SI NADA FUNCIONA

Como última opción:

1. Cierra Android Studio completamente
2. Elimina estas carpetas:
   - `.gradle/`
   - `.idea/caches/`
   - `app/build/`
3. Abre Android Studio nuevamente
4. Espera a que sincronice automáticamente
5. `File → Invalidate Caches / Restart`

---

## 📝 NOTA IMPORTANTE

**Los warnings (WARNING) NO bloquean la compilación.**

Solo los **ERROR(400)** impiden que compile.

Actualmente tienes:
- ❌ 1 ERROR (obtenerPorId) ← Este es el que debemos resolver
- ⚠️ Varios WARNING (no son críticos)

---

**Actualizado:** 15/01/2026 20:10

