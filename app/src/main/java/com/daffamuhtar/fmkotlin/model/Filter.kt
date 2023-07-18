package com.daffamuhtar.fmkotlin.model

data class Filter(
    var isActive: Boolean,
    val stageId: Int,
    val stageName: String,
)
