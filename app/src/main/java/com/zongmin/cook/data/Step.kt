package com.zongmin.cook.data

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.parcelize.Parcelize

@Parcelize
data class Step(
    val sequence: String = " ",
    val images: List<String> = listOf(" "),
    val depiction: String = " ",
    val toolType: ToolType = ToolType(" ", " ")
) : Parcelable

@Parcelize
data class ToolType(
    val temperature: String = " ",
    val timer: String = " "
) : Parcelable