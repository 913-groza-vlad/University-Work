package com.example.activityplannerapp.activities.domain

import com.example.activityplannerapp.R

data class ActivityType (
    val name: String,
    val picture: Int
)

object Types {
    val typesList = listOf(
        ActivityType("sport", R.drawable.sports),
        ActivityType("social", R.drawable.social),
        ActivityType("work", R.drawable.work),
        ActivityType("leisure", R.drawable.leisure),
        ActivityType("health", R.drawable.health),
        ActivityType("education", R.drawable.education),
        ActivityType("entertainment", R.drawable.entertainment),
        ActivityType("other", R.drawable.other_stuff)
    )
}