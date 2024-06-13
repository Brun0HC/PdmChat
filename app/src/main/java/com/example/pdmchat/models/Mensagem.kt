package com.example.pdmchat.models

import android.os.Parcelable
import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Parcelize
data class Mensagem (

    var id: String? = "",
    var receiver: String= "",
    @NonNull
    var sender: String = "",
    @NonNull
    var date: String = "",
    @NonNull
    var time: String = "",
    @NonNull
    var message: String = ""
): Parcelable