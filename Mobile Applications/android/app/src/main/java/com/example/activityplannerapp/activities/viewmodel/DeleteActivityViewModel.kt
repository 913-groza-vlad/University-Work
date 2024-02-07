package com.example.activityplannerapp.activities.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.activityplannerapp.activities.domain.Activity
import com.example.activityplannerapp.activities.usecase.DeleteActivityUseCase
import com.example.activityplannerapp.activities.usecase.DeleteActivityUseCaseImpl
import com.example.activityplannerapp.activities.usecase.GetActivityByIdUseCase
import kotlinx.coroutines.launch

class DeleteActivityViewModel(
    val useCase: DeleteActivityUseCase,
    val getActivityByIdUseCase: GetActivityByIdUseCase,
) : ViewModel() {

    private val _deletionConfirmed = mutableStateOf(false)
    val deletionConfirmed: State<Boolean> = _deletionConfirmed

    private val _activity = MutableLiveData<Activity?>()
    val activity: LiveData<Activity?> = _activity

    fun getActivityById(activityId: Int) {
        viewModelScope.launch {
            val activity = getActivityByIdUseCase(activityId)
            _activity.postValue(activity)
        }
    }

    fun confirmDeletion(activityId: Int) {
        viewModelScope.launch {
            val activity = getActivityByIdUseCase(activityId)
            useCase(activity)
            _deletionConfirmed.value = true
        }
    }

    fun cancelDeletion() {
        _deletionConfirmed.value = false
    }
}