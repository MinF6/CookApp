package com.zongmin.cook.data

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.parcelize.Parcelize

@Parcelize
data class Step(
    val sequence: String = " ",
    val images: List<String>,
    val depiction: String = " ",
    @Json(name = "tool_type") val toolType: ToolType
) : Parcelable

@Parcelize
data class ToolType(
    val temperature: String = " ",
    val timer: Int = 0
) : Parcelable