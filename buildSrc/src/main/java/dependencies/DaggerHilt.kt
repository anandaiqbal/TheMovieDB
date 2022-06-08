package dependencies

import org.gradle.api.artifacts.dsl.DependencyHandler

fun DependencyHandler.daggerHilt(){
    implementation("com.google.dagger:hilt-android:2.38.1")
    kapt("com.google.dagger:hilt-android-compiler:2.38.1")
}