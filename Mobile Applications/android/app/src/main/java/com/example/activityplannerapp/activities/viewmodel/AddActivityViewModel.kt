package com.example.activityplannerapp.activities.viewmodel

import android.util.Log
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.activityplannerapp.activities.domain.Activity
import com.example.activityplannerapp.activities.usecase.AddActivityUseCase
import com.example.activityplannerapp.activities.usecase.AddActivityUseCaseImpl
import com.example.activityplannerapp.activities.usecase.GetActivitiesUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalTime


class AddActivityViewModel(
    val useCase: AddActivityUseCase,
): ViewModel() {
    val activity = mutableStateOf(Activity(title = "", type = "", date = LocalDate.now(), startTime = LocalTime.now(), endTime = LocalTime.now(), description = ""))
    val errorMessage = mutableStateOf("")

    fun addActivity(activity: Activity) {
        viewModelScope.launch {
            try {
                useCase(activity)
            } catch (e: Exception) {
                Log.d("AddActivityViewModel", e.message.toString())
                errorMessage.value = e.message.toString()
            }
        }

    }

}