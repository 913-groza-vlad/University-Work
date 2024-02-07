package com.example.activityplannerapp.activities.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.activityplannerapp.activities.domain.Activity
import com.example.activityplannerapp.activities.usecase.GetActivityByIdUseCaseImpl
import com.example.activityplannerapp.activities.viewmodel.DeleteActivityViewModel

@Composable
fun DeleteActivityScreen(
    viewModel: DeleteActivityViewModel,
    activityId: Int,
    onConfirm: () -> Unit,
    onCancel: () -> Unit
) {
    Column(
        modifier =  Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        viewModel.getActivityById(activityId)
        val activity = viewModel.activity.observeAsState()

        Text("Activity Info:\n ${activity.value}")

        // Implement UI for confirmation and cancellation, and call respective functions
        Row(
            modifier = Modifier
                .padding(top = 50.dp)
                .fillMaxSize(0.7F),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(
                onClick = { onConfirm() },
            ) {
                Text("Confirm Deletion")
            }

            Button(onClick = { onCancel() }) {
                Text("Cancel")
            }
        }
    }

}