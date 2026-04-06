plugins {
    alias(libs.plugins.vpn.android.application)
    alias(libs.plugins.vpn.android.application.compose)
    alias(libs.plugins.vpn.hilt)
}

android {
    namespace = "com.org.vpndemo"

    defaultConfig {
        applicationId = "com.org.vpndemo"
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
}

dependencies {
    implementation(projects.data.home)
    implementation(projects.features.home)
    implementation(projects.core.designSystem)

    implementation(libs.androidx.activity.compose)
    implementation(libs.coil.compose)
    implementation(libs.coil.svg)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}
