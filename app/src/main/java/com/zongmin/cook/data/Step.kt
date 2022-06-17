package com.zongmin.cook.data

import com.squareup.moshi.Json

data class Step(
    val sequence: Int = 0,
    val images: List<String>,
    val depiction: String = " ",
    @Json(name = "tool_type") val toolType: ToolType
)

data class ToolType(
    val temperature: String = " ",
    val timer: Int = 0
)