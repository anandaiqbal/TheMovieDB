package dependencies.retrofit_okhttp

import dependencies.implementation
import org.gradle.api.artifacts.dsl.DependencyHandler

fun DependencyHandler.gson() {
    implementation("com.google.code.gson:gson:+")
}