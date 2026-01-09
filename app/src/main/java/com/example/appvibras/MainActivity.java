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

/**
 * Controlador para la pantalla de Login.
 */
public class MainActivity extends AppCompatActivity {

    private TextInputEditText etUsuario;
    private TextInputEditText etContrasena;
    private Button btnIngresar;
    private GestorUsuarios gestorUsuarios;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 1. Inicializar la Vista
        etUsuario = findViewById(R.id.et_usuario);
        etContrasena = findViewById(R.id.et_contrasena);
        btnIngresar = findViewById(R.id.btn_ingresar);

        // 2. Inicializar el Modelo
        gestorUsuarios = new GestorUsuarios(this);
        
        // Crear usuario por defecto si es la primera vez (para pruebas)
        gestorUsuarios.crearUsuarioDefecto();

        // 3. Manejar Eventos
        btnIngresar.setOnClickListener(v -> intentarLogin());
    }

    private void intentarLogin() {
        String usuarioStr = etUsuario.getText().toString();
        String contrasenaStr = etContrasena.getText().toString();

        Usuario usuario = gestorUsuarios.iniciarSesion(usuarioStr, contrasenaStr);

        if (usuario != null) {
            Toast.makeText(this, getString(R.string.bienvenida) + ": " + usuario.getNombreCompleto(), Toast.LENGTH_SHORT).show();
            // Ir al menú principal
            Intent intent = new Intent(this, MenuPrincipalActivity.class);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(this, getString(R.string.error_login), Toast.LENGTH_LONG).show();
        }
    }
}
