import com.android.build.api.dsl.ApplicationExtension
import extensions.Constants
import extensions.configureKotlinAndroid
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

class AndroidApplicationConventionPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.android.application")
                apply("org.jetbrains.kotlin.android")
            }

            extensions.configure<ApplicationExtension> {
                with(defaultConfig) {
                    targetSdk = Constants.TARGET_SDK
                    versionCode = 1
                    versionName = "${Constants.Version.MAJOR}.${Constants.Version.MINOR}.${Constants.Version.PATCH}"
                    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
                }

                bundle {
                    language {
                        enableSplit = false
                    }
                }

                configureKotlinAndroid(this)
            }
        }
    }
}