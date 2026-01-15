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
     * Muestra un mensaje de ÉXITO (verde) moderno y elegante
     */
    private void mostrarSnackbarExito(String mensaje) {
        mostrarMensajeModerno(mensaje, true);
    }

    /**
     * Muestra un mensaje de ERROR (rojo) moderno y elegante
     */
    private void mostrarSnackbarError(String mensaje) {
        mostrarMensajeModerno(mensaje, false);
    }

    /**
     * Muestra un mensaje moderno con diseño tipo Card, similar al login
     * @param mensaje Texto a mostrar
     * @param esExito true para verde (éxito), false para rojo (error)
     */
    private void mostrarMensajeModerno(String mensaje, boolean esExito) {
        // Crear el card contenedor
        androidx.cardview.widget.CardView cardMensaje = new androidx.cardview.widget.CardView(this);

        // Configurar el card
        android.widget.FrameLayout.LayoutParams cardParams = new android.widget.FrameLayout.LayoutParams(
            android.widget.FrameLayout.LayoutParams.MATCH_PARENT,
            android.widget.FrameLayout.LayoutParams.WRAP_CONTENT
        );
        cardParams.setMargins(32, 100, 32, 0); // Margen: 100dp desde arriba (debajo de la cámara)
        cardParams.gravity = android.view.Gravity.TOP;
        cardMensaje.setLayoutParams(cardParams);

        // Estilo del card
        cardMensaje.setCardElevation(16f); // Sombra pronunciada
        cardMensaje.setRadius(20f); // Bordes muy redondeados
        cardMensaje.setCardBackgroundColor(esExito ?
            Color.parseColor("#10B981") : // Verde moderno
            Color.parseColor("#EF4444"));  // Rojo moderno

        // Crear el LinearLayout interno
        android.widget.LinearLayout layout = new android.widget.LinearLayout(this);
        layout.setOrientation(android.widget.LinearLayout.HORIZONTAL);
        layout.setGravity(android.view.Gravity.CENTER_VERTICAL);
        layout.setPadding(24, 20, 24, 20); // Padding generoso

        // Crear el icono
        TextView iconoView = new TextView(this);
        iconoView.setText(esExito ? "✓" : "✕"); // ✓ para éxito, ✕ para error
        iconoView.setTextSize(28);
        iconoView.setTextColor(Color.WHITE);
        iconoView.setTypeface(null, android.graphics.Typeface.BOLD);
        android.widget.LinearLayout.LayoutParams iconParams =
            new android.widget.LinearLayout.LayoutParams(
                android.widget.LinearLayout.LayoutParams.WRAP_CONTENT,
                android.widget.LinearLayout.LayoutParams.WRAP_CONTENT
            );
        iconParams.setMarginEnd(16);
        iconoView.setLayoutParams(iconParams);

        // Crear el TextView del mensaje
        TextView mensajeView = new TextView(this);
        mensajeView.setText(mensaje);
        mensajeView.setTextSize(16);
        mensajeView.setTextColor(Color.WHITE);
        mensajeView.setTypeface(null, android.graphics.Typeface.BOLD);
        android.widget.LinearLayout.LayoutParams mensajeParams =
            new android.widget.LinearLayout.LayoutParams(
                0,
                android.widget.LinearLayout.LayoutParams.WRAP_CONTENT,
                1f
            );
        mensajeView.setLayoutParams(mensajeParams);

        // Agregar vistas al layout
        layout.addView(iconoView);
        layout.addView(mensajeView);
        cardMensaje.addView(layout);

        // Obtener el root view
        android.view.ViewGroup rootView = findViewById(android.R.id.content);
        rootView.addView(cardMensaje);

        // Animación de entrada (fade in + slide down)
        cardMensaje.setAlpha(0f);
        cardMensaje.setTranslationY(-50f);
        cardMensaje.animate()
            .alpha(1f)
            .translationY(0f)
            .setDuration(400)
            .setInterpolator(new android.view.animation.DecelerateInterpolator())
            .start();

        // Remover el card después de 3.5 segundos con animación
        new android.os.Handler().postDelayed(() -> {
            cardMensaje.animate()
                .alpha(0f)
                .translationY(-50f)
                .setDuration(400)
                .setInterpolator(new android.view.animation.AccelerateInterpolator())
                .withEndAction(() -> rootView.removeView(cardMensaje))
                .start();
        }, 3500);
    }

    /**
     * Muestra un mensaje de error genérico
     */
    private void mostrarError(String mensaje) {
        mostrarSnackbarError(mensaje);
    }
}
