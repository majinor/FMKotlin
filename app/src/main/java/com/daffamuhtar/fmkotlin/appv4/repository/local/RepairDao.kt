package com.daffamuhtar.fmkotlin.appv4.repository.local

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.daffamuhtar.fmkotlin.appv4.model.RepairEntity

@Dao
interface RepairDao {

    @Upsert
    suspend fun upsertAll(repairs: List<RepairEntity>)

    @Query("SELECT * FROM repairentity")
    fun pagingSource(): PagingSource<Int, RepairEntity>

    @Query("DELETE FROM repairentity")
    suspend fun clearAll()
}