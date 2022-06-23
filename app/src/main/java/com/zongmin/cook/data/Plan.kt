package com.zongmin.cook.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.*


@Parcelize
data class Plan(
    val id: String = " ",
    val name: String = " ",
    val threeMeals: String = " ",
    val time: Date? = null
) : Parcelable