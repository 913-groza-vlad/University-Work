package com.example.activityplannerapp.activities.server

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import retrofit2.Retrofit
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType

object ActivityAPI {
    private const val BASE_URL = "http://10.0.2.2:8080/activity/"

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
        .build()

    val retrofitService: ActivityService by lazy { retrofit.create(ActivityService::class.java) }

    var isConnectionOn by mutableStateOf(true)
    var connectionError = mutableStateOf("")
}