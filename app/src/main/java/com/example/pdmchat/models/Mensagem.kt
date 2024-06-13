package com.example.pdmchat.models

import android.os.Parcelable
import androidx.annotation.NonNull
import kotlinx.parcelize.Parcelize

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