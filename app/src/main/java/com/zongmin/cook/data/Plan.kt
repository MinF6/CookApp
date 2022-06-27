package com.zongmin.cook.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.*


@Parcelize
data class Plan(
    val id: String = " ",
    val threeMeals: String = " ",
    val planContent: PlanContent = PlanContent(" "," "," ",null)
) : Parcelable


@Parcelize
data class PlanContent(
    val image: String = " ",
    val name: String = " ",
    val category: String = " ",
    val time: Date? = null
): Parcelable