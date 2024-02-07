package com.example.activityplannerapp.activities.viewmodel

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.activityplannerapp.activities.domain.Activity
import com.example.activityplannerapp.activities.usecase.GetActivitiesUseCase
import com.example.activityplannerapp.activities.usecase.GetActivitiesUseCaseImpl
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

//@HiltViewModel
class ActivitiesViewModel (
    val useCase: GetActivitiesUseCase
): ViewModel() {
    private val _activitiesList = MutableStateFlow<List<Activity>>(emptyList())
    val activitiesList: StateFlow<List<Activity>> = _activitiesList

    private val _loading = MutableStateFlow(true)
    val loading: StateFlow<Boolean> = _loading

    init {
        viewModelScope.launch {
            try {
                useCase().collect { activitiesList ->
                    _loading.value = false
                    _activitiesList.value = activitiesList
                }
            }
            catch (e: Exception) {
                Log.d("ActivitiesViewModel", e.message.toString())
                _loading.value = false
            }

        }
    }

    fun refreshActivities() {
        viewModelScope.launch {
            try {
                _loading.value = true
                useCase().collect { activitiesList ->
                    _loading.value = false
                    _activitiesList.value = activitiesList
                }
            }
            catch (e: Exception) {
                Log.d("ActivitiesViewModel", e.message.toString())
                _loading.value = false
            }

        }
    }

}