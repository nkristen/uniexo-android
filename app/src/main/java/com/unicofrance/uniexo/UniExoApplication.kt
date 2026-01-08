package com.unicofrance.uniexo

import android.app.Application
import androidx.room.Room
import com.skydoves.retrofit.adapters.result.ResultCallAdapterFactory
import com.unicofrance.uniexo.data.local.database.AppDatabase
import com.unicofrance.uniexo.data.remote.Api
import com.unicofrance.uniexo.data.repositories.ContainerRepository
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class UniExoApplication : Application() {

    private val api: Api by lazy {
        Retrofit.Builder()
            .baseUrl("https://exo.dev.unicofrance.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(ResultCallAdapterFactory.create())
            .client(
                OkHttpClient().newBuilder()
                    .connectTimeout(10000L, TimeUnit.MILLISECONDS)
                    .readTimeout(10000L, TimeUnit.MILLISECONDS)
                    .writeTimeout(10000L, TimeUnit.MILLISECONDS).build()
            )
            .build()
            .create(Api::class.java)
    }

    private val database by lazy {
        Room.databaseBuilder(
            this,
            AppDatabase::class.java, "database"
        ).fallbackToDestructiveMigration(true).build()
    }

    val containerRepository by lazy {
        ContainerRepository(this, database.containerDao(), api)
    }

    override fun onCreate() {
        super.onCreate()
        MainScope().launch {
            containerRepository.fillIfEmpty()
        }
    }
}