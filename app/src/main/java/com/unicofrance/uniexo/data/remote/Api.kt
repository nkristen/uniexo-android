package com.unicofrance.uniexo.data.remote

import com.unicofrance.uniexo.data.local.database.entities.Container
import retrofit2.http.GET

interface Api {
    @GET("container")
    suspend fun getContainers() : Result<MutableList<Container>>
}
