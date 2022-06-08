package dependencies

import org.gradle.api.artifacts.dsl.DependencyHandler

fun DependencyHandler.youtubePlayer() {
    implementation ("com.pierfrancescosoffritti.androidyoutubeplayer:core:11.0.1")
    implementation ("com.pierfrancescosoffritti.androidyoutubeplayer:chromecast-sender:0.26")
}