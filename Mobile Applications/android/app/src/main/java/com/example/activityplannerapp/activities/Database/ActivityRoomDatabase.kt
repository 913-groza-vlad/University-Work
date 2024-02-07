package com.example.activityplannerapp.activities.Database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.activityplannerapp.activities.domain.Activity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalTime

@Database(entities = [Activity::class], version = 3, exportSchema = false)
@TypeConverters(Converters::class)
abstract class ActivityRoomDatabase: RoomDatabase() {
    abstract fun activityDao(): ActivityDao

    companion object {
        @Volatile
        private var INSTANCE: ActivityRoomDatabase? = null

        fun getDatabase(
            context: Context,
            scope: CoroutineScope
        ): ActivityRoomDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ActivityRoomDatabase::class.java,
                    "activity_database"
                )
                    // Wipes and rebuilds instead of migrating if no Migration object.
                    .fallbackToDestructiveMigration()
                    .addCallback(ActivityDatabaseCallback(scope))
                    .allowMainThreadQueries()
                    .build()
                INSTANCE = instance
                // return instance
                instance
            }
        }

        private class ActivityDatabaseCallback(
            private val scope: CoroutineScope
        ) : RoomDatabase.Callback() {
            override fun onOpen(db: SupportSQLiteDatabase) {
                super.onOpen(db)
                // If you want to keep the data through app restarts,
                // comment out the following line.
//                INSTANCE?.let { database ->
//                    scope.launch(Dispatchers.IO) {
//                        populateDatabase(database.activityDao())
//                    }
//                }
            }
        }

        /**
         * Populate the database in a new coroutine.
         */
        fun populateDatabase(activityDao: ActivityDao) {
            activityDao.deleteAll()

            var activity = Activity(title = "Jogging", type = "sport", date = LocalDate.of(2023, 12, 27), startTime = LocalTime.of(12, 15), endTime = LocalTime.of(13, 30), description = "jogging in the Central Park")
            activityDao.insert(activity)
            activity = Activity(title = "Weekly Meeting", type = "work", date = LocalDate.of(2023, 10, 31), startTime = LocalTime.of(9, 30), endTime = LocalTime.of(10, 0), description = "call with the clients")
            activityDao.insert(activity)
            activity = Activity(title = "Hip Hop Party", type = "social", date = LocalDate.of(2023, 11, 3), startTime = LocalTime.of(22, 0), endTime = LocalTime.of(23, 59), description = "party with friends")
            activityDao.insert(activity)
            activity = Activity(title = "AI Project", type = "education", date = LocalDate.of(2023, 11, 11), startTime = LocalTime.of(8, 20), endTime = LocalTime.of(11, 30), description = "working on the AI assignment")
            activityDao.insert(activity)
            activity = Activity(title = "Dinner", type = "social", date = LocalDate.of(2023, 11, 15), startTime = LocalTime.of(19, 0), endTime = LocalTime.of(20, 0), description = "dinner with family")
            activityDao.insert(activity)
            activity = Activity(title = "Dentist Appointment", type = "health", date = LocalDate.of(2023, 11, 30), startTime = LocalTime.of(14, 0), endTime = LocalTime.of(16, 30), description = "solving a tooth decay problem")
            activityDao.insert(activity)
        }
    }
}