package com.example.activityplannerapp.activities.repo

import android.util.Log
import com.example.activityplannerapp.activities.Database.ActivityDao
import com.example.activityplannerapp.activities.domain.Activity
import com.example.activityplannerapp.activities.server.ActivityAPI.connectionError
import com.example.activityplannerapp.activities.server.ActivityAPI.isConnectionOn
import com.example.activityplannerapp.activities.server.ActivityService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Call

interface ActivitiesRepository {
    suspend fun getActivities(): Flow<List<Activity>>
    suspend fun getActivityById(id: Int?): Activity

    suspend fun addActivity(activity: Activity)

    suspend fun updateActivity(activity: Activity)

    suspend fun deleteActivity(activity: Activity)

    suspend fun getLocalActivityById(id: Int?): Activity

    suspend fun addLocalActivity(activity: Activity)

    suspend fun updateLocalActivity(activity: Activity)

}

//class ActivitiesRepositoryImpl(private val activityDao: ActivityDao) : ActivitiesRepository{
//
//    override suspend fun getActivities(): Flow<List<Activity>> {
//        return activityDao.getAll()
//    }
//
//    override suspend fun getActivityById(id: Int?): Activity {
//        return activityDao.findById(id)
//    }
//
//    override suspend fun addActivity(activity: Activity) {
//        activityDao.insert(activity)
//    }
//
//    override suspend fun updateActivity(activity: Activity) {
//        activityDao.update(activity)
//    }
//
//    override suspend fun deleteActivity(activity: Activity) {
//        activityDao.delete(activity)
//    }
//}

class ActivitiesRepositoryImpl(private val retrofitService: ActivityService, private val activityDao: ActivityDao) : ActivitiesRepository{


    override suspend fun getActivities(): Flow<List<Activity>> {
        try {
            val activities = retrofitService.getAllActivities()
            if (!isConnectionOn)
                connectionError.value = "Server is back online"
            isConnectionOn = true
        } catch (e: Exception) {
            Log.d("ActivitiesRepository", "could not get activities from server, getting from local database")
            isConnectionOn = false
            connectionError.value = "Server is offline, getting activities from local db"
            return activityDao.getAll()
        }
        return flow {
            emit(retrofitService.getAllActivities())
        }
    }

    override suspend fun getActivityById(id: Int?): Activity {
        return retrofitService.getActivityById(id!!.toLong())
    }

    override suspend fun addActivity(activity: Activity) {
        retrofitService.addActivity(activity)
        activityDao.insert(activity)
    }

    override suspend fun updateActivity(activity: Activity) {
        retrofitService.updateActivity(activity.id.toLong(), activity)
        activityDao.update(activity)
    }

    override suspend fun deleteActivity(activity: Activity) {
        retrofitService.deleteActivity(activity.id.toLong())
        activityDao.delete(activity)
    }


    override suspend fun getLocalActivityById(id: Int?): Activity {
        return activityDao.findById(id)
    }

    override suspend fun addLocalActivity(activity: Activity) {
        activityDao.insert(activity)
    }

    override suspend fun updateLocalActivity(activity: Activity) {
        activityDao.update(activity)
    }

}