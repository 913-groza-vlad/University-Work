package com.example.activityplannerapp.activities.server

import com.example.activityplannerapp.activities.domain.Activity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ActivityService {
    @GET("getAll")
    suspend fun getAllActivities(): List<Activity>

    @GET("get/{id}")
    suspend fun getActivityById(@Path("id") id: Long): Activity

    @POST("add")
    suspend fun addActivity(@Body activity: Activity): Activity

    @PUT("update/{id}")
    suspend fun updateActivity(@Path("id") id: Long, @Body activity: Activity): Activity

    @DELETE("delete/{id}")
    suspend fun deleteActivity(@Path("id") id: Long)
}