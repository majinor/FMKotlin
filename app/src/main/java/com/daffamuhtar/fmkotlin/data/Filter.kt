package com.daffamuhtar.fmkotlin.data

data class Filter(
    var isActive: Boolean,
    val stageId: Int,
    val stageName: String,
)
