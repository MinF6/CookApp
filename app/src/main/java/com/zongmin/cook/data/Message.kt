package com.zongmin.cook.data

import com.squareup.moshi.Json
import java.sql.Timestamp

data class Message(
    val id: String = " ",
    @Json(name = "user_id") val userId: String = " ",
    val content: String = " ",
    val time: Timestamp? = null
)