package com.example.appvibras;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import com.example.appvibras.controlador.MenuPrincipalActivity;
import com.example.appvibras.modelo.entidades.Usuario;
import com.example.appvibras.modelo.gestores.GestorUsuarios;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

/**
 * MainActivity - Controlador de Login (Capa de Presentación - Modelo Tres Capas)
 *
 * Características implementadas:
 * - Formulario de login con usuario y contraseña
 * - Contraseña oculta/visible con toggle
 * - Personalización del mensaje de bienvenida con nombre del usuario
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
    private TextView tvMensajeBienvenida;
    private TextView tvTituloFormulario;
    private TextView tvSubtituloFormulario;

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
        tvMensajeBienvenida = findViewById(R.id.tv_mensaje_bienvenida);
        tvTituloFormulario = findViewById(R.id.tv_titulo_formulario);
        tvSubtituloFormulario = findViewById(R.id.tv_subtitulo_formulario);
    }

    /**
     * Configura los listeners de eventos
     */
    private void configurarEventos() {
        btnIngresar.setOnClickListener(v -> intentarLogin());

        // Personalizar mensaje de bienvenida mientras escribe el usuario
        if (etUsuario != null) {
            etUsuario.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    actualizarMensajeBienvenida(s.toString().trim());
                }

                @Override
                public void afterTextChanged(Editable s) {}
            });

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
     * Actualiza el mensaje de bienvenida y el título con el nombre del usuario
     */
    private void actualizarMensajeBienvenida(String nombreUsuario) {
        if (tvMensajeBienvenida == null) return;

        if (nombreUsuario.isEmpty()) {
            // Mensaje por defecto - Sin repetir "Inicio de Sesión"
            tvMensajeBienvenida.setText("¡Bienvenido! Ingresa tus credenciales para continuar");

            if (tvTituloFormulario != null) {
                tvTituloFormulario.setText("👋 Bienvenido");
            }

            if (tvSubtituloFormulario != null) {
                tvSubtituloFormulario.setText("Accede a tu cuenta");
            }
        } else {
            // Buscar el usuario en la base de datos para obtener su nombre completo
            Usuario usuarioEncontrado = gestorUsuarios.buscarPorUsername(nombreUsuario);

            String nombreAMostrar;
            if (usuarioEncontrado != null && usuarioEncontrado.getNombres() != null) {
                // Usar el nombre completo del usuario de la BD
                nombreAMostrar = usuarioEncontrado.getNombres();
            } else {
                // Si no se encuentra, capitalizar el username
                nombreAMostrar = nombreUsuario.substring(0, 1).toUpperCase() +
                               (nombreUsuario.length() > 1 ? nombreUsuario.substring(1).toLowerCase() : "");
            }

            // Mensaje personalizado con nombre completo
            tvMensajeBienvenida.setText("✨ ¡Qué bueno verte, " + nombreAMostrar + "! Ingresa tu contraseña");

            if (tvTituloFormulario != null) {
                tvTituloFormulario.setText("Hola, " + nombreAMostrar);
            }

            if (tvSubtituloFormulario != null) {
                tvSubtituloFormulario.setText("Accede a tu cuenta");
            }
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
            // ✅ Login exitoso - Snackbar VERDE
            String mensajeBienvenida = "✅ ¡Bienvenido, " + usuario.getNombres() + "!";
            mostrarSnackbarExito(mensajeBienvenida);

            // Ir al menú principal después de un pequeño delay
            new android.os.Handler().postDelayed(() -> {
                Intent intent = new Intent(MainActivity.this, MenuPrincipalActivity.class);
                intent.putExtra("USUARIO_NOMBRE", usuario.getNombres());
                intent.putExtra("USUARIO_ID", usuario.getId());
                startActivity(intent);
                finish();
            }, 1000); // 1 segundo para que vea el mensaje
        } else {
            // ❌ Login fallido - Snackbar ROJO
            String mensajeError = gestorUsuarios.getUltimoError();

            if (mensajeError.contains("Contraseña incorrecta")) {
                if (tilContrasena != null) {
                    tilContrasena.setError("❌ Contraseña incorrecta");
                }
                etContrasena.requestFocus();
                etContrasena.selectAll();
                mostrarSnackbarError("❌ Contraseña incorrecta");
            } else if (mensajeError.contains("Usuario no encontrado")) {
                if (tilUsuario != null) {
                    tilUsuario.setError("❌ Usuario no encontrado");
                }
                etUsuario.requestFocus();
                mostrarSnackbarError("❌ Usuario no existe");
            } else {
                mostrarSnackbarError(mensajeError.isEmpty() ?
                    "❌ Usuario o contraseña incorrectos" : "❌ " + mensajeError);
            }
        }
    }

    /**
     * Muestra un Snackbar de ÉXITO (verde) en la parte superior derecha
     */
    private void mostrarSnackbarExito(String mensaje) {
        View rootView = findViewById(android.R.id.content);
        Snackbar snackbar = Snackbar.make(rootView, mensaje, Snackbar.LENGTH_LONG);

        // Personalizar colores - VERDE para éxito
        View snackbarView = snackbar.getView();
        snackbarView.setBackgroundColor(Color.parseColor("#10B981")); // Verde éxito

        TextView textView = snackbarView.findViewById(com.google.android.material.R.id.snackbar_text);
        textView.setTextColor(Color.WHITE);
        textView.setTextSize(16);
        textView.setTypeface(null, android.graphics.Typeface.BOLD);

        snackbar.show();
    }

    /**
     * Muestra un Snackbar de ERROR (rojo) en la parte superior
     */
    private void mostrarSnackbarError(String mensaje) {
        View rootView = findViewById(android.R.id.content);
        Snackbar snackbar = Snackbar.make(rootView, mensaje, Snackbar.LENGTH_LONG);

        // Personalizar colores - ROJO para error
        View snackbarView = snackbar.getView();
        snackbarView.setBackgroundColor(Color.parseColor("#EF4444")); // Rojo error

        // Posicionar en la parte SUPERIOR
        android.widget.FrameLayout.LayoutParams params =
            (android.widget.FrameLayout.LayoutParams) snackbarView.getLayoutParams();
        params.gravity = android.view.Gravity.TOP | android.view.Gravity.CENTER_HORIZONTAL;
        params.topMargin = 50; // Margen desde arriba
        snackbarView.setLayoutParams(params);

        TextView textView = snackbarView.findViewById(com.google.android.material.R.id.snackbar_text);
        textView.setTextColor(Color.WHITE);
        textView.setTextSize(16);
        textView.setTypeface(null, android.graphics.Typeface.BOLD);

        snackbar.show();
    }

    /**
     * Muestra un mensaje de error genérico
     */
    private void mostrarError(String mensaje) {
        mostrarSnackbarError("❌ " + mensaje);
    }
}
