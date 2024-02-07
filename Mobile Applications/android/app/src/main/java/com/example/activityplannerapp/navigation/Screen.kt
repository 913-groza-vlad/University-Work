package com.example.activityplannerapp.navigation

sealed class Screen(val route: String) {
    object Activities : Screen("activities")
    object AddActivity : Screen("add_activity")
    object UpdateActivity : Screen("edit_activity")
    object DeleteActivity: Screen("delete_activity")
}