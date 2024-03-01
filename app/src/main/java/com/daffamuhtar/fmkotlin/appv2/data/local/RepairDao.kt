package com.daffamuhtar.fmkotlin.appv2.data.local

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert

@Dao
interface RepairDao {

    @Upsert
    suspend fun upsertAll(repairs: List<RepairEntity>)

    @Query("SELECT * FROM repairentity")
    fun pagingSource(): PagingSource<Int, RepairEntity>

    @Query("DELETE FROM repairentity")
    suspend fun clearAll()
}