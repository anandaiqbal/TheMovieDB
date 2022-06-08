package dependencies

import org.gradle.api.artifacts.dsl.DependencyHandler

fun DependencyHandler.glide() {
    implementation("com.github.bumptech.glide:glide:4.13.0")
    annotationProcessor("com.github.bumptech.glide:compiler:4.13.0")
}