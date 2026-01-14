package com.example.appvibras.utils;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import com.example.appvibras.R;
import com.example.appvibras.controlador.MenuPrincipalActivity;

/**
 * Clase helper para configurar la navegación estándar en todas las Activities.
 * Proporciona métodos comunes para configurar botones de navegación (back, home).
 */
public class NavigationHelper {

    /**
     * Configura los botones de navegación estándar (back y home) en una Activity.
     *
     * @param activity La Activity donde se configurarán los botones
     */
    public static void setupNavigationButtons(Activity activity) {
        // Botón de regresar
        View btnBack = activity.findViewById(R.id.btn_back);
        if (btnBack != null) {
            btnBack.setOnClickListener(v -> activity.finish());
        }

        // Botón de home (ir al menú principal)
        View btnHome = activity.findViewById(R.id.btn_home);
        if (btnHome != null) {
            btnHome.setOnClickListener(v -> goToHome(activity));
        }
    }

    /**
     * Navega al menú principal limpiando el stack de activities.
     *
     * @param activity La Activity desde donde se navega
     */
    public static void goToHome(Activity activity) {
        Intent intent = new Intent(activity, MenuPrincipalActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        activity.startActivity(intent);
        activity.finish();
    }

    /**
     * Vuelve a la Activity anterior.
     *
     * @param activity La Activity actual
     */
    public static void goBack(Activity activity) {
        activity.finish();
    }
}

