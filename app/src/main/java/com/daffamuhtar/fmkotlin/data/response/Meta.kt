package com.daffamuhtar.fmkotlin.data.response

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Meta(
    val date: String,
    val page: Int,
    val pages: Int,
    val perpage: Int,
    val stage: String,
    val total: Int
) : Parcelable