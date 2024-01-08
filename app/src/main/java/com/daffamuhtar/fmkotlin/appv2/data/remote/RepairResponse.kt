package com.daffamuhtar.fmkotlin.appv2.data.remote

data class RepairResponse(
    val id: Int,
    val name: String,
    val tagline: String,
    val description: String,
    val first_brewed: String,
    val image_url: String?
)
