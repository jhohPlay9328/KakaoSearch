package com.jhoh.play.app

import android.app.Application
import com.jhoh.play.data.BuildConfig
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class SearchApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        if(BuildConfig.DEBUG) Timber.plant(Timber.DebugTree())
    }
}
