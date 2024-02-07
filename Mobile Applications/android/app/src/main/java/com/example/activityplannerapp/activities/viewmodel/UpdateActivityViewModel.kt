package com.example.activityplannerapp.activities.viewmodel

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.activityplannerapp.activities.domain.Activity
import com.example.activityplannerapp.activities.usecase.GetActivityByIdUseCase
import com.example.activityplannerapp.activities.usecase.UpdateActivityUseCase
import kotlinx.coroutines.launch

class UpdateActivityViewModel(
    val useCase: UpdateActivityUseCase,
    val getActivityByIdUseCase: GetActivityByIdUseCase
): ViewModel() {
    private val _activity = MutableLiveData<Activity?>()
    val activity: LiveData<Activity?> = _activity
    val errorMessage = mutableStateOf("")

    fun getActivityById(activityId: Int) {
        viewModelScope.launch {
            val activity = getActivityByIdUseCase(activityId)
            _activity.postValue(activity)
        }
    }

    fun confirmUpdate(activity: Activity) {
        viewModelScope.launch {
            try {
                useCase(activity)
            } catch (e: Exception) {
                Log.d("UpdateActivityViewModel", e.message.toString())
                errorMessage.value = e.message.toString()
            }
        }
    }

}