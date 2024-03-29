package com.zongmin.cook.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.*


@Parcelize
data class Plan(
    var id: String = "",
    var userId: String = "",
    val threeMeals: String = "",
    val time: Long = 0L,
    val planContent: PlanContent = PlanContent()
) : Parcelable


@Parcelize
data class PlanContent(
    val foodId: String = "",
    val image: String = "",
    val name: String = "",
    val category: String = "",
): Parcelable