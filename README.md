# 📦 AppVibras - Sistema de Gestión de Inventario

## 📋 Descripción

AppVibras es una aplicación Android para la gestión integral de inventarios, que permite controlar productos, categorías, clientes, proveedores, compras y ventas de manera eficiente.

## ✨ Características

- 🔐 **Sistema de Login** - Autenticación de usuarios
- 📊 **Menú Principal** - Acceso rápido a todos los módulos
- 🏷️ **Gestión de Categorías** - Organiza tus productos por categorías
- 📦 **Gestión de Productos** - Control completo del inventario con marca e industria
- 👥 **Gestión de Clientes** - Registro de clientes
- 📥 **Entradas (Compras)** - Registro de compras a proveedores
- 📤 **Salidas (Ventas)** - Registro de ventas a clientes
- 📈 **Reportes** - (Próximamente)
- 🔄 **Navegación intuitiva** - Botones de retroceso y home en todas las vistas

## 🛠️ Tecnologías

- **Lenguaje:** Java
- **Framework:** Android SDK
- **Base de Datos:** Room (SQLite)
- **UI:** Material Design Components
- **Arquitectura:** MVC (Modelo-Vista-Controlador)

## 📁 Estructura del Proyecto

```
AppVibras/
├── app/src/main/
│   ├── java/com/example/appvibras/
│   │   ├── controlador/        # Activities (Controladores)
│   │   ├── modelo/
│   │   │   ├── entidades/      # Entidades de la BD
│   │   │   ├── gestores/       # Lógica de negocio
│   │   │   └── base/           # Configuración de Room
│   │   ├── utils/              # Utilidades y helpers
│   │   └── vistas/             # Adaptadores personalizados
│   └── res/
│       ├── drawable/           # Iconos y recursos gráficos
│       ├── layout/             # Layouts XML
│       └── values/             # Strings, Colors, Themes
├── docs/                       # Documentación
│   ├── CORRECCIONES_Y_MEJORAS.md
│   ├── INSTRUCCIONES.md
│   ├── credenciales.txt
│   ├── estructuras.txt
│   └── requerimientos.txt
└── README.md
```

## 🚀 Compilación y Ejecución

### Requisitos Previos

- Android Studio Arctic Fox o superior
- JDK 11 o superior
- Android SDK API 24+ (Android 7.0)

### Pasos para Compilar

1. **Clonar o abrir el proyecto:**
   ```
   Abre Android Studio → File → Open → Selecciona AppVibras
   ```

2. **Sincronizar Gradle:**
   - Android Studio sincronizará automáticamente
   - Si no, haz clic en "Sync Now"

3. **Compilar:**
   - Build → Rebuild Project

4. **Ejecutar:**
   - Run → Run 'app'
   - O presiona Shift + F10

### Compilación desde Terminal (Opcional)

```batch
.\compilar.bat
```

**Nota:** Si experimentas problemas con Java 25, compila desde Android Studio.

## 📱 Módulos de la Aplicación

### 1. Login
- Autenticación de usuarios
- Diseño moderno con Material Design

### 2. Menú Principal
- 6 opciones principales organizadas en cards
- Navegación intuitiva a cada módulo

### 3. Categorías
- Crear, leer, actualizar y eliminar categorías
- Vista de lista con información organizada

### 4. Productos
- CRUD completo de productos
- Campos: Nombre, Descripción, Precio, Marca, Industria, Categoría
- Control de stock
- Vista detallada con toda la información

### 5. Clientes
- Gestión completa de clientes
- Información de contacto y ubicación

### 6. Entradas (Compras)
- Registro de compras a proveedores
- Actualización automática de stock

### 7. Salidas (Ventas)
- Registro de ventas a clientes
- Control de inventario en tiempo real

## 🎨 Diseño

- **Paleta de colores:** Tonos morados y azules profesionales
- **Tipografía:** Roboto (Material Design)
- **Componentes:** Material Design 3
- **Navegación:** Bottom navigation y botones de acción flotantes

## 📊 Base de Datos

### Entidades

- **Usuario** - Información de usuarios del sistema
- **Categoria** - Categorías de productos
- **Producto** - Productos del inventario
- **Cliente** - Información de clientes
- **Proveedor** - Información de proveedores
- **Venta** - Cabecera de ventas
- **DetalleVenta** - Detalle de productos vendidos
- **Compra** - Registro de compras
- **MovimientoStock** - Historial de movimientos

## 🔧 Últimas Correcciones (14/01/2026)

✅ Archivos XML corrupto corregidos
✅ Archivos obsoletos eliminados
✅ Navegación completa implementada
✅ Iconos personalizados creados
✅ Scripts de compilación mejorados
✅ Documentación completa

Ver [CORRECCIONES_Y_MEJORAS.md](docs/CORRECCIONES_Y_MEJORAS.md) para más detalles.

## 📝 Próximas Mejoras

- [ ] Implementar módulo de Reportes
- [ ] Mejorar diseño del Login
- [ ] Agregar validaciones de formularios
- [ ] Implementar búsqueda y filtros
- [ ] Agregar exportación de datos
- [ ] Implementar gráficos de estadísticas
- [ ] Optimizar queries con índices en Room

## 📄 Licencia

Proyecto académico - Universidad [Nombre de tu Universidad]

## 👥 Autor

Luis - [LuissxD]

## 📞 Soporte

Para problemas o dudas:
1. Revisa la carpeta `docs/`
2. Consulta el Logcat de Android Studio
3. Verifica el archivo de INSTRUCCIONES.md

---

**Última actualización:** 14 de enero de 2026
**Versión:** 1.0.0

