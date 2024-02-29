package com.daffamuhtar.fmkotlin.appv4.repository.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.daffamuhtar.fmkotlin.appv4.model.RepairEntity


@Database(
    entities = [RepairEntity::class],
    version = 1
)
abstract class FleetifyMechanicDatabase : RoomDatabase() {

    abstract val dao: RepairDao
}
