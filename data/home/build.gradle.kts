plugins {
    alias(libs.plugins.vpn.android.library)
    alias(libs.plugins.vpn.hilt)
}

android {
    namespace = "com.org.data.home"
}

dependencies {
    implementation(projects.core.network)
    implementation(projects.core.database)

    implementation(projects.domain.home)
}
