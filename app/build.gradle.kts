plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.appvibras"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.example.appvibras"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    // Configuración para carpetas de vistas por módulo
    sourceSets {
        getByName("main") {
            res.srcDirs(
                "src/main/res/vistas/comunes",
                "src/main/res/vistas/categorias",
                "src/main/res/vistas/productos",
                "src/main/res/vistas/clientes",
                "src/main/res/vistas/ventas",
                "src/main/res/vistas/compras",
                "src/main/res/vistas/login",
                "src/main/res/vistas/menu",
                "src/main/res"
            )
        }
    }
}

dependencies {
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    
    implementation(libs.room.runtime)
    annotationProcessor(libs.room.compiler)

    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}