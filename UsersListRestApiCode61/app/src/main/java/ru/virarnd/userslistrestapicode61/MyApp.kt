package ru.virarnd.userslistrestapicode61

import android.app.Application
import timber.log.Timber
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.module.AppGlideModule;

class MyApp : Application() {
    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
    }
}

@GlideModule
class MyGlideApp : AppGlideModule()
