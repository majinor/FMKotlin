package com.daffamuhtar.fmkotlin.appv2.domain.repository

import com.daffamuhtar.fmkotlin.appv2.api.RepairOrderServices


class RepairRepository (
    private val apiServices: RepairOrderServices,
) {
    suspend fun getPopularMoviesList(page: Int) = apiServices.getRepairOnAdhocList(page)
//    suspend fun getMovieDetails(id: Int) = apiServices.getMovieDetails(id)
}