package dependencies

import org.gradle.api.artifacts.dsl.DependencyHandler

fun DependencyHandler.lottie() {
    implementation("com.airbnb.android:lottie:3.4.0")
}