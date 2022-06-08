package dependencies

import org.gradle.api.artifacts.dsl.DependencyHandler

fun DependencyHandler.recyclerView(){
    implementation("androidx.recyclerview:recyclerview:1.2.1")
    implementation("androidx.recyclerview:recyclerview-selection:1.1.0")
}