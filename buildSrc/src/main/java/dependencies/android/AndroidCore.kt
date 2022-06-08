package dependencies

import org.gradle.api.artifacts.dsl.DependencyHandler

fun DependencyHandler.androidCore() {
    implementation("androidx.core:core-ktx:1.7.0")
    implementation("androidx.appcompat:appcompat:1.4.1")
}