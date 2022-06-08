package id.indocyber.themoviedb

import android.app.Application
import android.util.Log
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class TheMovieDB : Application() {

    override fun onCreate() {
        super.onCreate()
        Thread.setDefaultUncaughtExceptionHandler { t, e ->
            Log.e("Application Crash", "Application Error", e)
        }
    }
}