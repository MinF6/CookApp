package com.zongmin.cook

import android.app.Application
import com.zongmin.cook.data.source.CookRepository
import com.zongmin.cook.util.ServiceLocator
import kotlin.properties.Delegates

class CookApplication: Application() {

    // Depends on the flavor,
    val cookRepository: CookRepository
        get() = ServiceLocator.provideTasksRepository(this)

    companion object {
        var instance: CookApplication by Delegates.notNull()
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}