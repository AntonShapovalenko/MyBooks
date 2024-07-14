package com.books.app

import android.app.Application
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class BookApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        FirebaseRemoteConfig.getInstance().fetchAndActivate()
    }
}