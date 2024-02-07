package com.example.activityplannerapp.activities.Database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.activityplannerapp.activities.domain.Activity
import kotlinx.coroutines.flow.Flow

@Dao
interface ActivityDao {

    @Query("SELECT * FROM activities ORDER BY id")
    fun getAll(): Flow<List<Activity>>

    @Query("SELECT * FROM activities WHERE id = :id")
    fun findById(id: Int?): Activity

    //@Insert(onConflict = OnConflictStrategy.IGNORE)
    @Insert
    fun insert(activity: Activity)

    @Delete
    fun delete(activity: Activity)

    @Update
    fun update(activity: Activity)

    @Query("DELETE FROM activities")
    fun deleteAll()
}