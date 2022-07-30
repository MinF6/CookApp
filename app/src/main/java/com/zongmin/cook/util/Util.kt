package com.zongmin.cook.util

import com.zongmin.cook.CookApplication

object Util {

    fun getString(resourceId: Int): String {
        return CookApplication.instance.getString(resourceId)
    }

}