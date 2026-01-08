package com.unicofrance.uniexo.data.repositories

import android.content.Context
import android.util.Log
import com.unicofrance.uniexo.data.local.database.CsvParser
import com.unicofrance.uniexo.data.local.database.entities.Container
import com.unicofrance.uniexo.data.local.database.entities.ContainerDao
import com.unicofrance.uniexo.data.remote.Api
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.collections.emptyList

class ContainerRepository(
    private val context: Context,
    private val containerDao: ContainerDao,
    private val api: Api
) {

    suspend fun fillIfEmpty() {
        if (containerDao.hasData()) {
            Log.d("Parsing step", "Database already filled")
            return
        }

        val containers = withContext(Dispatchers.IO) {
            val parser = CsvParser(context)
            context.assets
                .open("containers.csv")
                .use { parser.parse(it) }

//            val result = api.getContainers()
//            result.getOrThrow()
        }

        containerDao.insertAll(containers)
        Log.d("Parsing step", " ${containers.size} containers inserted in database")
    }

    fun getAll() = containerDao.getAll()

    suspend fun insert(container: Container) = containerDao.insert(container)

    suspend fun deleteAll() = containerDao.deleteAll()
}