package com.gla.hostelmart

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class HostelMartApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        // Initialize any app-wide dependencies here
    }
}
