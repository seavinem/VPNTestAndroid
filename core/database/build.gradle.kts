plugins {
    alias(libs.plugins.vpn.android.library)
    alias(libs.plugins.vpn.hilt)
    alias(libs.plugins.vpn.android.room)
}

android {
    namespace = "com.org.core.database"
}
