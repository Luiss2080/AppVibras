package com.example.appvibras;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.appvibras.controlador.MenuPrincipalActivity;
import com.example.appvibras.modelo.entidades.Usuario;
import com.example.appvibras.modelo.gestores.GestorUsuarios;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

/**
 * MainActivity - Controlador de Login (Capa de Presentación - Modelo Tres Capas)
 *
 * Características implementadas:
 * - Formulario de login con usuario y contraseña
 * - Contraseña oculta/visible con toggle
 * - Validación de campos vacíos
 * - Mensajes específicos de error (usuario no encontrado, contraseña incorrecta)
 * - Integración con modelo de tres capas
 */
public class MainActivity extends AppCompatActivity {

    // Vistas
    private TextInputEditText etUsuario;
    private TextInputEditText etContrasena;
    private TextInputLayout tilUsuario;
    private TextInputLayout tilContrasena;
    private Button btnIngresar;

    // Modelo (Capa de Negocio)
    private GestorUsuarios gestorUsuarios;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_index);

        // 1. Inicializar Vistas
        inicializarVistas();

        // 2. Inicializar Modelo (Capa de Negocio)
        gestorUsuarios = new GestorUsuarios(this);
        
        // Crear usuario por defecto si no existe ninguno
        gestorUsuarios.crearUsuarioDefecto();

        // 3. Configurar Eventos
        configurarEventos();
    }

    /**
     * Inicializa todas las vistas del layout
     */
    private void inicializarVistas() {
        tilUsuario = findViewById(R.id.til_usuario);
        tilContrasena = findViewById(R.id.til_contrasena);
        etUsuario = findViewById(R.id.et_usuario);
        etContrasena = findViewById(R.id.et_contrasena);
        btnIngresar = findViewById(R.id.btn_ingresar);
    }

    /**
     * Configura los listeners de eventos
     */
    private void configurarEventos() {
        btnIngresar.setOnClickListener(v -> intentarLogin());

        // Limpiar errores al escribir
        if (etUsuario != null) {
            etUsuario.setOnFocusChangeListener((v, hasFocus) -> {
                if (hasFocus && tilUsuario != null) {
                    tilUsuario.setError(null);
                }
            });
        }

        if (etContrasena != null) {
            etContrasena.setOnFocusChangeListener((v, hasFocus) -> {
                if (hasFocus && tilContrasena != null) {
                    tilContrasena.setError(null);
                }
            });
        }
    }

    /**
     * Intenta iniciar sesión con las credenciales ingresadas
     * Muestra mensajes específicos según el tipo de error
     */
    private void intentarLogin() {
        // Limpiar errores previos
        if (tilUsuario != null) tilUsuario.setError(null);
        if (tilContrasena != null) tilContrasena.setError(null);

        // Validar que los campos existan
        if (etUsuario == null || etUsuario.getText() == null ||
            etContrasena == null || etContrasena.getText() == null) {
            mostrarError("Error al leer los campos del formulario");
            return;
        }

        // Obtener valores
        String usuarioStr = etUsuario.getText().toString().trim();
        String contrasenaStr = etContrasena.getText().toString();

        // Validar campos vacíos
        if (usuarioStr.isEmpty()) {
            if (tilUsuario != null) {
                tilUsuario.setError("El usuario es obligatorio");
            }
            etUsuario.requestFocus();
            return;
        }

        if (contrasenaStr.isEmpty()) {
            if (tilContrasena != null) {
                tilContrasena.setError("La contraseña es obligatoria");
            }
            etContrasena.requestFocus();
            return;
        }

        // Intentar autenticación con el gestor (Capa de Negocio)
        Usuario usuario = gestorUsuarios.iniciarSesion(usuarioStr, contrasenaStr);

        if (usuario != null) {
            // Login exitoso
            String mensajeBienvenida = "¡Bienvenido, " + usuario.getNombres() + "!";
            Toast.makeText(this, mensajeBienvenida, Toast.LENGTH_SHORT).show();

            // Ir al menú principal
            Intent intent = new Intent(this, MenuPrincipalActivity.class);
            intent.putExtra("USUARIO_NOMBRE", usuario.getNombres());
            intent.putExtra("USUARIO_ID", usuario.getId());
            startActivity(intent);
            finish();
        } else {
            // Login fallido - Obtener mensaje de error específico
            String mensajeError = gestorUsuarios.getUltimoError();

            if (mensajeError.contains("Contraseña incorrecta")) {
                // ⭐ Mostrar error específico de contraseña incorrecta
                if (tilContrasena != null) {
                    tilContrasena.setError("❌ Contraseña incorrecta");
                }
                etContrasena.requestFocus();
                etContrasena.selectAll();
                Toast.makeText(this, "⚠️ La contraseña ingresada es incorrecta",
                             Toast.LENGTH_LONG).show();
            } else if (mensajeError.contains("Usuario no encontrado")) {
                if (tilUsuario != null) {
                    tilUsuario.setError("❌ Usuario no encontrado");
                }
                etUsuario.requestFocus();
                Toast.makeText(this, "⚠️ El usuario no existe", Toast.LENGTH_LONG).show();
            } else {
                // Error genérico
                mostrarError(mensajeError.isEmpty() ?
                    "Usuario o contraseña incorrectos" : mensajeError);
            }
        }
    }

    /**
     * Muestra un mensaje de error genérico
     */
    private void mostrarError(String mensaje) {
        Toast.makeText(this, "❌ " + mensaje, Toast.LENGTH_LONG).show();
    }
}
