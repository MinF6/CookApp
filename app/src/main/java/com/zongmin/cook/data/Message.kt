package com.zongmin.cook.data

import android.os.Parcelable

import com.squareup.moshi.Json
import kotlinx.parcelize.Parcelize
import java.sql.Timestamp
import java.util.*

@Parcelize
data class Message(
    val id: String = " ",
    val userId: String = " ",
    val content: String = " ",
//    val time: Timestamp? = null
    val time: Date? = null
//    val time: Long = 0L
) : Parcelable