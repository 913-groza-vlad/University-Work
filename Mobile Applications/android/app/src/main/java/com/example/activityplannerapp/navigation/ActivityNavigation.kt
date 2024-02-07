package com.example.activityplannerapp.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.room.Room
import com.example.activityplannerapp.activities.Database.ActivityRoomDatabase
import com.example.activityplannerapp.activities.domain.Activity
import com.example.activityplannerapp.activities.repo.ActivitiesRepositoryImpl
import com.example.activityplannerapp.activities.screens.ActivitiesScreen
import com.example.activityplannerapp.activities.screens.AddActivityScreen
import com.example.activityplannerapp.activities.screens.DeleteActivityScreen
import com.example.activityplannerapp.activities.screens.UpdateActivityScreen
import com.example.activityplannerapp.activities.server.ActivityAPI
import com.example.activityplannerapp.activities.service.ActivitiesService
import com.example.activityplannerapp.activities.usecase.AddActivityUseCaseImpl
import com.example.activityplannerapp.activities.usecase.DeleteActivityUseCaseImpl
import com.example.activityplannerapp.activities.usecase.GetActivitiesUseCase
import com.example.activityplannerapp.activities.usecase.GetActivitiesUseCaseImpl
import com.example.activityplannerapp.activities.usecase.GetActivityByIdUseCase
import com.example.activityplannerapp.activities.usecase.GetActivityByIdUseCaseImpl
import com.example.activityplannerapp.activities.usecase.UpdateActivityUseCase
import com.example.activityplannerapp.activities.usecase.UpdateActivityUseCaseImpl
import com.example.activityplannerapp.activities.viewmodel.ActivitiesViewModel
import com.example.activityplannerapp.activities.viewmodel.AddActivityViewModel
import com.example.activityplannerapp.activities.viewmodel.DeleteActivityViewModel
import com.example.activityplannerapp.activities.viewmodel.UpdateActivityViewModel


@Preview(showBackground = true)
@Composable
fun ActivityNavigation() {
    val navController = rememberNavController()
    val coroutineScope = rememberCoroutineScope()

    val context = LocalContext.current

    val activityDao = remember {
        ActivityRoomDatabase.getDatabase(
            context,
            coroutineScope
        ).activityDao()
    }
    val repo = ActivitiesRepositoryImpl(ActivityAPI.retrofitService, activityDao)
    val service = ActivitiesService(repo)
    val getActivitiesUseCase = GetActivitiesUseCaseImpl(service)
    val activitiesViewModel = ActivitiesViewModel(getActivitiesUseCase)

    val addActivityUseCase = AddActivityUseCaseImpl(service)
    val deleteActivityUseCase = DeleteActivityUseCaseImpl(service)
    val updateActivityUseCase = UpdateActivityUseCaseImpl(service)

    NavHost(navController = navController, startDestination = Screen.Activities.route) {
        composable(route = Screen.Activities.route) {
            ActivitiesScreen(
                viewModel = activitiesViewModel,
                onActivityEditClick = { selectedActivity: Activity ->
                    navController.navigate(Screen.UpdateActivity.route + "/${selectedActivity.id}")
                },
                onActivityDeleteClick = { selectedActivity: Activity ->
                    navController.navigate(Screen.DeleteActivity.route + "/${selectedActivity.id}")
                },
                onActivityAddClick = {
                    navController.navigate(Screen.AddActivity.route)
                }
            )
        }

        composable(
            route = "${Screen.UpdateActivity.route}/{activityId}",
            arguments = listOf(navArgument("activityId") {type = NavType.IntType})
        ) { backStackEntry ->
            val activityId = backStackEntry.arguments?.getInt("activityId")
            val getActivityUseCase = GetActivityByIdUseCaseImpl(service)
            val viewModel: UpdateActivityViewModel = UpdateActivityViewModel(updateActivityUseCase, getActivityUseCase)

            if (activityId != null) {
                UpdateActivityScreen(
                    viewModel = viewModel,
                    activitiesViewModel = activitiesViewModel,
                    activityId = activityId,
                    onUpdate = {updatedActivity ->
                        viewModel.confirmUpdate(updatedActivity)
                        if (viewModel.errorMessage.value == "") {
                            navController.popBackStack()
                        }
                    },
                    onCancel = {
                        navController.popBackStack()
                    }
                )
            }
        }

        composable(route = Screen.AddActivity.route) {
            val viewModel: AddActivityViewModel = AddActivityViewModel(addActivityUseCase)
            AddActivityScreen(
                viewModel = viewModel,
                activitiesViewModel = activitiesViewModel,
                onAdd = { newActivity ->
                    viewModel.addActivity(newActivity)
                    if (viewModel.errorMessage.value == "") {
                        navController.popBackStack()
                    }
                },
                onCancel = {
                    navController.popBackStack()
                }
            )
        }

        composable(
            route = "${Screen.DeleteActivity.route}/{activityId}",
            arguments = listOf(navArgument("activityId") {type = NavType.IntType})
        ) { backStackEntry ->
            val activityId = backStackEntry.arguments?.getInt("activityId")
            val getActivityUseCase = GetActivityByIdUseCaseImpl(service)
            val deleteActivityViewModel: DeleteActivityViewModel = DeleteActivityViewModel(deleteActivityUseCase, getActivityUseCase)

            if (activityId != null) {
                DeleteActivityScreen(
                    viewModel = deleteActivityViewModel,
                    activityId = activityId,
                    onConfirm = {
                        deleteActivityViewModel.confirmDeletion(activityId) // Handle deletion in the ViewModel
                        navController.popBackStack()
                    },
                    onCancel = {
                        deleteActivityViewModel.cancelDeletion()
                        navController.popBackStack() // Handle cancellation
                    }
                )
            }
        }

    }

}