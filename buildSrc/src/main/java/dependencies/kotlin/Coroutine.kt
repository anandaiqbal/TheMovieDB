package dependencies.kotlin

import dependencies.implementation
import org.gradle.api.artifacts.dsl.DependencyHandler

fun DependencyHandler.coroutine() {
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.0")
}