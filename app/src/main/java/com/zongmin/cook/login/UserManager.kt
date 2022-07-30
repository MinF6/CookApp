package com.zongmin.cook.login

import android.content.Context
import com.zongmin.cook.CookApplication
import com.zongmin.cook.data.User

/**
 * Created by Wayne Chen in Jul. 2019.
 */
object UserManager {

    private const val USER_DATA = "user_data"
    private const val USER_TOKEN = "user_token"

    var user = User()

    var userToken: String? = null
        get() = CookApplication.instance
            .getSharedPreferences(USER_DATA, Context.MODE_PRIVATE)
            .getString(USER_TOKEN, null)
        set(value) {
            field = when (value) {
                null -> {
                    CookApplication.instance
                        .getSharedPreferences(USER_DATA, Context.MODE_PRIVATE).edit()
                        .remove(USER_TOKEN)
                        .apply()
                    null
                }
                else -> {
                    CookApplication.instance
                        .getSharedPreferences(USER_DATA, Context.MODE_PRIVATE).edit()
                        .putString(USER_TOKEN, value)
                        .apply()
                    value
                }
            }
        }

}
