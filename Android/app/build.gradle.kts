import java.util.Properties

val localProperties: Properties = Properties().apply {
    val localPropertiesFile = rootProject.file("local.properties")
    if (localPropertiesFile.exists()) {
        load(localPropertiesFile.inputStream())
    }
}

plugins {
    id("org.mozilla.rust-android-gradle.rust-android")
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "io.dispersia.opensculpt"
    compileSdk = 35
    ndkVersion = localProperties.getProperty("ndkVersion")

    defaultConfig {
        applicationId = "io.dispersia.opensculpt"
        minSdk = 28
        targetSdk = 35
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
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
      viewBinding = true
        prefab = true
    }
    sourceSets {
      getByName("androidTest") {
        jniLibs.srcDir("${layout.buildDirectory}/rustJniLibs/android")
      }
      getByName("debug") {
        jniLibs.srcDir("${layout.buildDirectory}/rustJniLibs/android")
      }
    }
}

cargo {
    module = "../../crates/android"
    libname = "open_sculpt"
    targets = listOf("x86_64", "x86", "arm", "arm64")
    targetDirectory = "../../target"
}

tasks.whenTaskAdded {
  if (name == "javaPreCompileDebug" || name == "javaPreCompilerRelease") {
    dependsOn("cargoBuild")
  }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.games.activity)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}
