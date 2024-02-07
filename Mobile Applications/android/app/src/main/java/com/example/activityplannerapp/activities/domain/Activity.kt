package com.example.activityplannerapp.activities.domain

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter

//data class Activity(
//    val id: Int,
//    var title: String,
//    var type: String,
//    var date: LocalDate,
//    var startTime: LocalTime,
//    var endTime: LocalTime,
//    var description: String,
//) {
//
//    private fun formatDate(date: LocalDate): String {
//        val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
//        return date.format(formatter)
//    }
//
//    private fun formatTime(time: LocalTime): String {
//        val formatter = DateTimeFormatter.ofPattern("HH:mm")
//        return time.format(formatter)
//    }
//
//    override fun toString(): String {
//        return "$title \nDate: ${formatDate(date)} \nStart Time: ${formatTime(startTime)} \nEnd Time: ${formatTime(endTime)} \nDetails: $description"
//    }
//}

@Serializable
@Entity(tableName = "activities")
data class Activity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    var title: String,
    var type: String,

    @Contextual
    @Serializable(with = LocalDateSerializer::class)
    var date: LocalDate,

    @Contextual
    @Serializable(with = LocalTimeSerializer::class)
    var startTime: LocalTime,

    @Contextual
    @Serializable(with = LocalTimeSerializer::class)
    var endTime: LocalTime,
    var description: String,
) {
    private fun formatDate(date: LocalDate): String {
        val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
        return date.format(formatter)
    }

    private fun formatTime(time: LocalTime): String {
        val formatter = DateTimeFormatter.ofPattern("HH:mm")
        return time.format(formatter)
    }

    override fun toString(): String {
        return "$title \nDate: ${formatDate(date)} \nStart Time: ${formatTime(startTime)} \nEnd Time: ${formatTime(endTime)} \nDetails: $description"
    }
}