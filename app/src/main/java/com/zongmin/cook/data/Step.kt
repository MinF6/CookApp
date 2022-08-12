package com.zongmin.cook.data

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.parcelize.Parcelize

@Parcelize
data class Step(
    var id: String = "",
    var sequence: String = "",
    var images: String = "",
    var depiction: String = "",
    val toolType: ToolType = ToolType()
) : Parcelable

@Parcelize
data class ToolType(
    var temperature: String = "",
    var timer: String = ""
) : Parcelable