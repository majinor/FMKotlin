package com.daffamuhtar.fmkotlin.data.repository

import com.daffamuhtar.fmkotlin.data.api.ApiHelper

class MainRepository (private val apiHelper: ApiHelper) {
    suspend fun getCheckRepair(userId: String) =  apiHelper.getCheckRepair(userId)
}