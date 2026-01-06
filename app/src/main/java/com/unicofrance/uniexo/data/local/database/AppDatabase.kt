package com.unicofrance.uniexo.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.unicofrance.uniexo.data.local.database.entities.Container
import com.unicofrance.uniexo.data.local.database.entities.ContainerDao

@Database(entities = [Container::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun containerDao(): ContainerDao
}