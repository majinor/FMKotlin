package com.daffamuhtar.fmkotlin.appv2.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [RepairEntity::class],
    version = 1
)
abstract class MechanicDatabase: RoomDatabase() {

    abstract val dao: RepairDao
}