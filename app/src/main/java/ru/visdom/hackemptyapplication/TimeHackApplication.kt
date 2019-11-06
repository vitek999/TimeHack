package ru.visdom.hackemptyapplication

import android.app.Application
import timber.log.Timber

class TimeHackApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
    }
}