package extensions

import com.android.build.api.dsl.CommonExtension
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType
import org.jetbrains.kotlin.compose.compiler.gradle.ComposeCompilerGradlePluginExtension

fun Project.configureAndroidCompose(extension: CommonExtension<*, *, *, *, *, *>) {
    val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")
    extension.apply {
        buildFeatures {
            compose = true
        }
    }

    with(extensions.getByType<ComposeCompilerGradlePluginExtension>()) {
        // Only enable source info in debug builds
        includeSourceInformation.set(
            gradle.startParameter.taskNames.any { it.contains("Debug", ignoreCase = true) }
        )

        reportsDestination.set(layout.buildDirectory.dir("compose_compiler"))
    }

    dependencies {
        val bom = libs.findLibrary("androidx-compose-bom").get()
        "implementation"(platform(bom))
        "androidTestImplementation"(platform(bom))
        "implementation"(libs.findLibrary("androidx-compose-material3").get())
        "implementation"(libs.findLibrary("androidx-compose-ui-tooling-preview").get())
        "debugImplementation"(libs.findLibrary("androidx-compose-ui-tooling").get())
    }
}
