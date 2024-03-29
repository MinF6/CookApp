package com.zongmin.cook.util

import android.content.Context
import androidx.annotation.VisibleForTesting
import com.zongmin.cook.data.source.CookDataSource
import com.zongmin.cook.data.source.CookRepository
import com.zongmin.cook.data.source.DefaultCookRepository
import com.zongmin.cook.data.source.remote.CookRemoteDataSource

object ServiceLocator {

    @Volatile
    var cookRepository: CookRepository? = null
        @VisibleForTesting set

    fun provideTasksRepository(context: Context): CookRepository {
        synchronized(this) {
            return cookRepository
                ?: createStylishRepository(context)
        }
    }

    private fun createStylishRepository(context: Context): CookRepository {
        return DefaultCookRepository(
            CookRemoteDataSource
        )
    }

}