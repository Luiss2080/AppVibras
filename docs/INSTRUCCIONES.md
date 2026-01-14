# INSTRUCCIONES PARA COMPILAR Y EJECUTAR - AppVibras

## ¿Cómo compilar el proyecto?

### Opción 1: Desde Android Studio (RECOMENDADO)

1. Abre Android Studio
2. File → Open → Selecciona la carpeta `AppVibras`
3. Espera a que Gradle sincronice el proyecto
4. Build → Rebuild Project
5. Run → Run 'app'

Esta es la opción más confiable, ya que Android Studio usa su propio JDK.

### Opción 2: Desde la línea de comandos

Ejecuta el archivo `compilar.bat` que se encuentra en la raíz del proyecto:

```
.\compilar.bat
```

**NOTA:** Si experimentas errores con Java 25, usa la Opción 1.

## ¿Qué se ha corregido?

### Errores XML corregidos ✅
- Formato corrupto de `ic_product.xml`
- Formato corrupto de `bg_input_field.xml`

### Archivos obsoletos eliminados ✅
- `menu_index.xml`
- `dialogo_cliente.xml`
- `activity_categorias.xml`
- `activity_clientes.xml`

### Navegación implementada ✅
Todos los módulos ahora tienen:
- Botón "Atrás" para regresar a la vista anterior
- Botón "Home" para ir al menú principal

### Iconos creados ✅
- `ic_home.xml` - Icono de inicio
- `ic_back.xml` - Icono de retroceso

## Warnings que puedes ignorar

Durante la compilación verás algunos warnings de Room:
- "idProducto column references a foreign key but it is not part of an index"
- "Schema export directory was not provided"

**Estos warnings NO impiden que la aplicación funcione correctamente.**
Son optimizaciones opcionales que se pueden implementar después.

## Estructura del proyecto

```
AppVibras/
├── app/
│   ├── src/
│   │   └── main/
│   │       ├── java/com/example/appvibras/
│   │       │   ├── controlador/          (Activities)
│   │       │   ├── modelo/
│   │       │   │   ├── entidades/        (Entidades Room)
│   │       │   │   ├── gestores/         (Lógica de negocio)
│   │       │   │   └── base/             (Base de datos)
│   │       │   ├── utils/                (Utilidades)
│   │       │   └── vistas/               (Adaptadores)
│   │       └── res/
│   │           ├── drawable/             (Iconos)
│   │           ├── layout/               (Layouts XML)
│   │           └── values/               (Colors, Strings, etc.)
│   └── build.gradle.kts
├── docs/
│   ├── CORRECCIONES_Y_MEJORAS.md
│   └── INSTRUCCIONES.md
├── compilar.bat
└── build_project.bat
```

## Navegación de la aplicación

1. **Login** → Inicia sesión
2. **Menú Principal** → Accede a los módulos:
   - Categorías
   - Productos
   - Entradas (Compras)
   - Salidas (Ventas)
   - Reportes
   - Cerrar Sesión

3. Cada módulo tiene:
   - Lista de registros
   - Botón flotante (+) para agregar
   - Long-click para opciones (editar/eliminar)
   - Botón "Atrás" para regresar
   - Botón "Home" para ir al menú principal

## Solución de problemas

### Error: "El marcador en el documento que precede al elemento raíz debe tener el formato correcto"
**Solución:** Ya corregido en ic_product.xml y bg_input_field.xml

### Error: "resource android:drawable/ic_menu_home is private"
**Solución:** Ya corregido - ahora usa @drawable/ic_home

### Error: "java.lang.IllegalArgumentException: 25.0.1"
**Solución:** Compila desde Android Studio

### La aplicación se cierra al hacer clic en un botón del menú
**Verificar:**
1. Que todas las Activities estén registradas en AndroidManifest.xml
2. Que NavigationHelper esté correctamente implementado
3. Ver el Logcat en Android Studio para ver el error específico

## Próximos pasos

1. ✅ Compilar el proyecto desde Android Studio
2. ✅ Probar la navegación entre módulos
3. ⏳ Implementar marca e industria en tabla Producto (si falta)
4. ⏳ Agregar validaciones de formularios
5. ⏳ Implementar reportes
6. ⏳ Mejorar el diseño del Login

## Contacto y soporte

Si tienes problemas, revisa:
1. El archivo `CORRECCIONES_Y_MEJORAS.md` en la carpeta `docs`
2. El Logcat de Android Studio (View → Tool Windows → Logcat)
3. Los logs de compilación en la ventana Build de Android Studio

