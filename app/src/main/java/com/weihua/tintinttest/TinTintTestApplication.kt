package com.weihua.tintinttest

import android.app.Application
import com.weihua.tintinttest.di.TinTintTestModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.ksp.generated.module

class TinTintTestApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@TinTintTestApplication)
            modules(
                TinTintTestModule().module,
            )
        }
    }
}