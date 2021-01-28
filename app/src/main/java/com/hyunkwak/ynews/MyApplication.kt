package com.hyunkwak.ynews

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class MyApplication : Application() {
    init {
        Timber.plant(Timber.DebugTree())
    }
}