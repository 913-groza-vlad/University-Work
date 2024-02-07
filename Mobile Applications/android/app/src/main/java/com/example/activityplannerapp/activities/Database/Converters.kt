package com.example.activityplannerapp.activities.Database

import androidx.room.TypeConverter
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter

class Converters {
    @TypeConverter
    fun fromLocalTime(localTime: LocalTime): Long {
        return localTime.toSecondOfDay().toLong()
    }

    @TypeConverter
    fun toLocalTime(value: Long): LocalTime {
        return LocalTime.ofSecondOfDay(value)
    }

    private val formatter: DateTimeFormatter = DateTimeFormatter.ISO_LOCAL_DATE

    @TypeConverter
    fun fromLocalDate(localDate: LocalDate): String {
        return localDate.format(formatter)
    }

    @TypeConverter
    fun toLocalDate(value: String): LocalDate {
        return LocalDate.parse(value, formatter)
    }
}