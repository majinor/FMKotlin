package com.daffamuhtar.fmkotlin.domain.use_case

import com.daffamuhtar.fmkotlin.app.Resource
import com.daffamuhtar.fmkotlin.data.remote.response.toRepair
import com.daffamuhtar.fmkotlin.domain.model.Repair
import com.daffamuhtar.fmkotlin.domain.repository.RepairOrderRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

class GetRepairAdhocListUseCase constructor(
    val repository: RepairOrderRepository
) {

    operator fun invoke(
        userId: String,
        stageId: Int,
        searchQuery: String
    ): Flow<Resource<List<Repair>>> = flow {
        try {
            emit(Resource.Loading<List<Repair>>())
            val repairs = repository.getRepairOnAdhocs(userId, stageId, searchQuery).map { it.toRepair() }
            emit(Resource.Success<List<Repair>>(repairs))
        } catch(e: HttpException) {
            emit(Resource.Error<List<Repair>>(e.localizedMessage ?: "An unexpected error occured"))
        } catch(e: IOException) {
            emit(Resource.Error<List<Repair>>("Couldn't reach server. Check your internet connection."))
        }
    }

}