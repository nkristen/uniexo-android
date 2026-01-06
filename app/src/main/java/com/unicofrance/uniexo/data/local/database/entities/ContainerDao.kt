package com.unicofrance.uniexo.data.local.database.entities

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow

@Dao
interface ContainerDao {

    @Query("SELECT EXISTS(SELECT * FROM container LIMIT 1)")
    suspend fun hasData(): Boolean

    @Query("SELECT * FROM container")
    fun getAll(): Flow<List<Container>>

    @Insert
    suspend fun insert(container: Container)

    @Transaction
    suspend fun insertAll(containers: List<Container>) {
        containers.forEach{ insert(it) }
    }

    @Query("""DELETE FROM container""")
    suspend fun deleteAll()
}