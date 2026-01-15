# 🏗️ Modelo de Tres Capas - Sistema de Inicio de Sesión

## 📋 Arquitectura del Sistema

El sistema de inicio de sesión de AppVibras implementa el **Patrón de Arquitectura de Tres Capas** (Three-Tier Architecture) que separa las responsabilidades en tres niveles distintos.

---

## 🎯 Componentes del Modelo de Tres Capas

### 1️⃣ CAPA DE PRESENTACIÓN (Vista / UI)

**Archivos:**
- `MainActivity.java`
- `login_index.xml`

**Responsabilidades:**
- ✅ Interacción con el usuario
- ✅ Captura de datos del formulario
- ✅ Validación de entrada básica (campos vacíos)
- ✅ Presentación de resultados
- ✅ Navegación entre pantallas
- ✅ Feedback visual (Snackbars, errores en campos)

**Código de ejemplo:**
```java
public class MainActivity extends AppCompatActivity {
    private TextInputEditText etUsuario;
    private TextInputEditText etContrasena;
    private GestorUsuarios gestorUsuarios; // Conexión con Capa de Negocio
    
    private void intentarLogin() {
        String usuario = etUsuario.getText().toString().trim();
        String contrasena = etContrasena.getText().toString();
        
        // Llamada a la Capa de Negocio
        Usuario usuarioAutenticado = gestorUsuarios.iniciarSesion(usuario, contrasena);
        
        if (usuarioAutenticado != null) {
            mostrarSnackbarExito("¡Bienvenido!");
            irAlMenuPrincipal();
        } else {
            mostrarSnackbarError(gestorUsuarios.getUltimoError());
        }
    }
}
```

---

### 2️⃣ CAPA DE NEGOCIO (Lógica / Business Logic)

**Archivos:**
- `GestorUsuarios.java`
- `ValidadorUsuario.java` (opcional)

**Responsabilidades:**
- ✅ Lógica de negocio
- ✅ Validación de reglas de negocio
- ✅ Coordinación entre capas
- ✅ Procesamiento de datos
- ✅ Generación de mensajes de error específicos
- ✅ No accede directamente a la UI
- ✅ No maneja SQLite directamente

**Código de ejemplo:**
```java
public class GestorUsuarios {
    private BaseDatos db; // Conexión con Capa de Datos
    private String ultimoError;
    
    public Usuario iniciarSesion(String nombreUsuario, String contrasena) {
        // Validación de lógica de negocio
        if (nombreUsuario == null || nombreUsuario.isEmpty()) {
            ultimoError = "El usuario no puede estar vacío";
            return null;
        }
        
        // Llamada a la Capa de Datos
        Usuario usuario = db.usuarioDao().login(nombreUsuario, contrasena);
        
        if (usuario == null) {
            // Lógica para determinar el tipo de error
            Usuario existe = db.usuarioDao().buscarPorNombreUsuario(nombreUsuario);
            ultimoError = (existe == null) ? 
                "Usuario no encontrado" : "Contraseña incorrecta";
        }
        
        return usuario;
    }
    
    public String getUltimoError() {
        return ultimoError;
    }
}
```

---

### 3️⃣ CAPA DE DATOS (Persistencia / Data Access)

**Archivos:**
- `Usuario.java` (Entidad)
- `UsuarioDao.java` (Data Access Object)
- `BaseDatos.java` (Room Database)

**Responsabilidades:**
- ✅ Acceso a la base de datos
- ✅ Operaciones CRUD
- ✅ Ejecución de queries SQL
- ✅ Mapeo de datos
- ✅ No contiene lógica de negocio
- ✅ No interactúa con la UI

**Código de ejemplo:**

**Entidad:**
```java
@Entity(tableName = "usuarios")
public class Usuario {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String nombres;
    private String correoElectronico;
    private String celular;
    private String nombreUsuario;
    private String contrasena;
    
    // Constructor, getters y setters
}
```

**DAO:**
```java
@Dao
public interface UsuarioDao {
    @Query("SELECT * FROM usuarios WHERE nombreUsuario = :nombreUsuario AND contrasena = :contrasena LIMIT 1")
    Usuario login(String nombreUsuario, String contrasena);
    
    @Query("SELECT * FROM usuarios WHERE nombreUsuario = :nombreUsuario LIMIT 1")
    Usuario buscarPorNombreUsuario(String nombreUsuario);
    
    @Insert
    long insertar(Usuario usuario);
}
```

**Base de Datos:**
```java
@Database(entities = {Usuario.class, ...}, version = 4)
public abstract class BaseDatos extends RoomDatabase {
    public abstract UsuarioDao usuarioDao();
    
    public static synchronized BaseDatos obtenerInstancia(Context contexto) {
        // Implementación Singleton
    }
}
```

---

## 🔄 Flujo Completo de Autenticación

### Diagrama de Secuencia:

```
USUARIO                PRESENTACIÓN           NEGOCIO              DATOS
  |                    (MainActivity)      (GestorUsuarios)    (UsuarioDao)
  |                         |                    |                  |
  |--Click "Ingresar"------>|                    |                  |
  |                         |                    |                  |
  |                         |--iniciarSesion()-->|                  |
  |                         |                    |                  |
  |                         |                    |--login()-------->|
  |                         |                    |                  |
  |                         |                    |                  |--SELECT FROM usuarios
  |                         |                    |                  |
  |                         |                    |<--Usuario--------|
  |                         |                    |                  |
  |                         |<--Usuario----------|                  |
  |                         |                    |                  |
  |<--Snackbar Verde--------|                    |                  |
  |                         |                    |                  |
  |<--Redirige a Menú-------|                    |                  |
```

### Paso a Paso:

1. **USUARIO** ingresa credenciales y presiona "Ingresar"

2. **CAPA DE PRESENTACIÓN (MainActivity.java)**
   - Captura los datos del formulario
   - Valida que no estén vacíos
   - Llama: `gestorUsuarios.iniciarSesion(usuario, contrasena)`

3. **CAPA DE NEGOCIO (GestorUsuarios.java)**
   - Recibe los parámetros
   - Aplica validaciones de negocio
   - Llama: `db.usuarioDao().login(usuario, contrasena)`

4. **CAPA DE DATOS (UsuarioDao.java)**
   - Ejecuta la query SQL:
     ```sql
     SELECT * FROM usuarios 
     WHERE nombreUsuario = 'admin' 
     AND contrasena = 'admin123'
     ```
   - Retorna `Usuario` si existe, `null` si no

5. **CAPA DE NEGOCIO (GestorUsuarios.java)**
   - Procesa el resultado
   - Si es null, determina el tipo de error:
     - Busca el usuario solo por nombre
     - Si no existe → "Usuario no encontrado"
     - Si existe → "Contraseña incorrecta"
   - Retorna el Usuario o null a la capa superior

6. **CAPA DE PRESENTACIÓN (MainActivity.java)**
   - Recibe el resultado
   - Si es exitoso:
     - Muestra Snackbar verde: "✅ ¡Bienvenido, Luis Rocha!"
     - Redirige al menú principal
   - Si falló:
     - Obtiene el mensaje: `gestorUsuarios.getUltimoError()`
     - Muestra Snackbar rojo: "❌ Contraseña incorrecta"

---

## 📊 Tabla Comparativa de Responsabilidades

| Aspecto | Presentación | Negocio | Datos |
|---------|--------------|---------|-------|
| **Archivos** | MainActivity.java<br>login_index.xml | GestorUsuarios.java | Usuario.java<br>UsuarioDao.java<br>BaseDatos.java |
| **Usuario** | ✅ Interactúa | ❌ No interactúa | ❌ No interactúa |
| **Validación UI** | ✅ Campos vacíos | ❌ No | ❌ No |
| **Validación Negocio** | ❌ No | ✅ Reglas de negocio | ❌ No |
| **Base de Datos** | ❌ No accede | ❌ No accede directamente | ✅ Maneja |
| **Lógica de Negocio** | ❌ No contiene | ✅ Contiene | ❌ No contiene |
| **Mensajes UI** | ✅ Muestra | ✅ Genera | ❌ No maneja |
| **Navegación** | ✅ Controla | ❌ No controla | ❌ No controla |
| **Dependencias** | Depende de Negocio | Depende de Datos | No depende de nadie |

---

## 🎯 Ventajas del Modelo de Tres Capas

### 1. **Separación de Responsabilidades**
- Cada capa tiene un propósito claro
- Cambios en una capa no afectan a las otras
- Código más organizado y mantenible

### 2. **Reutilización de Código**
- La lógica de negocio puede usarse en múltiples vistas
- Los DAOs pueden usarse desde cualquier gestor
- Las entidades son independientes

### 3. **Facilidad de Pruebas**
- Cada capa se puede probar independientemente
- Mock objects para simular capas
- Unit tests más simples

### 4. **Escalabilidad**
- Fácil agregar nuevas funcionalidades
- Cambiar la UI sin tocar la lógica
- Cambiar la BD sin tocar la presentación

### 5. **Mantenibilidad**
- Bugs más fáciles de localizar
- Cambios localizados en una capa
- Código más limpio y legible

---

## 🔍 Ejemplo Práctico: Cambiar de SQLite a Firebase

### Sin Tres Capas (Todo en MainActivity):
```java
// ❌ PROBLEMA: Todo mezclado
public class MainActivity extends AppCompatActivity {
    private void login() {
        String user = etUser.getText().toString();
        String pass = etPass.getText().toString();
        
        // UI, Validación, BD todo junto
        if (user.isEmpty()) {
            etUser.setError("Campo vacío");
            return;
        }
        
        // Acceso directo a SQLite
        SQLiteDatabase db = getDatabase();
        Cursor cursor = db.query("usuarios", ...);
        
        // Si queremos cambiar a Firebase, hay que reescribir TODO
    }
}
```

### Con Tres Capas:
```java
// ✅ SOLUCIÓN: Capas separadas

// PRESENTACIÓN (no cambia)
private void login() {
    String user = etUser.getText().toString();
    Usuario usuario = gestorUsuarios.iniciarSesion(user, pass);
    // ...
}

// NEGOCIO (no cambia)
public Usuario iniciarSesion(String user, String pass) {
    return db.usuarioDao().login(user, pass);
}

// DATOS (solo cambia esta capa)
// Antes: SQLite
@Dao
public interface UsuarioDao {
    @Query("SELECT * FROM usuarios WHERE...")
    Usuario login(String user, String pass);
}

// Después: Firebase (solo cambiamos el DAO)
public class FirebaseUsuarioDao implements UsuarioDao {
    public Usuario login(String user, String pass) {
        // Nueva implementación con Firebase
        return firebaseAuth.signIn(user, pass);
    }
}
```

---

## 📁 Estructura de Archivos

```
app/src/main/java/com/example/appvibras/
│
├── controlador/                    # CAPA DE PRESENTACIÓN
│   └── MainActivity.java          ✓ Vista de login
│
├── modelo/
│   ├── gestores/                  # CAPA DE NEGOCIO
│   │   └── GestorUsuarios.java   ✓ Lógica de autenticación
│   │
│   ├── entidades/                 # CAPA DE DATOS
│   │   └── Usuario.java          ✓ Entidad
│   │
│   ├── dao/                       # CAPA DE DATOS
│   │   └── UsuarioDao.java       ✓ Data Access Object
│   │
│   └── base/                      # CAPA DE DATOS
│       └── BaseDatos.java        ✓ Room Database
│
app/src/main/res/layout/
└── login_index.xml                ✓ UI del login
```

---

## ✅ Checklist de Implementación

### CAPA DE PRESENTACIÓN ✅
- [x] MainActivity.java creado
- [x] login_index.xml diseñado
- [x] Captura de datos del formulario
- [x] Validación de campos vacíos
- [x] Llamadas a GestorUsuarios
- [x] Snackbars de éxito/error
- [x] Navegación al menú principal

### CAPA DE NEGOCIO ✅
- [x] GestorUsuarios.java creado
- [x] Método iniciarSesion() implementado
- [x] Método registrarUsuario() implementado
- [x] Método buscarPorUsername() implementado
- [x] Método getUltimoError() implementado
- [x] Validación de reglas de negocio
- [x] Diferenciación de tipos de error

### CAPA DE DATOS ✅
- [x] Usuario.java (Entidad) creado
- [x] UsuarioDao.java (DAO) creado
- [x] BaseDatos.java configurado
- [x] Query login() implementada
- [x] Query buscarPorNombreUsuario() implementada
- [x] Operaciones CRUD completas
- [x] SembradorBaseDatos.java para datos iniciales

---

## 🎓 Conclusión

El sistema de inicio de sesión de AppVibras implementa correctamente el **Modelo de Tres Capas**, separando:

1. **Presentación** → Interacción con usuario (MainActivity + XML)
2. **Negocio** → Lógica de aplicación (GestorUsuarios)
3. **Datos** → Persistencia (Usuario + UsuarioDao + BaseDatos)

Esta arquitectura garantiza:
- ✅ Código mantenible
- ✅ Fácil de escalar
- ✅ Testeable
- ✅ Reutilizable
- ✅ Profesional

---

**Fecha de implementación:** 15/01/2026  
**Desarrollador:** Luis Rocha  
**Versión:** 1.0  
**Estado:** ✅ Completamente funcional

