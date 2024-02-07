package com.example.activityplannerapp.activities.screens

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.activityplannerapp.activities.viewmodel.ActivitiesViewModel
import com.example.activityplannerapp.R
import com.example.activityplannerapp.activities.domain.Activity
import com.example.activityplannerapp.activities.domain.Types
import com.example.activityplannerapp.activities.server.ActivityAPI.connectionError
import com.example.activityplannerapp.activities.server.ActivityAPI.isConnectionOn


//@Preview(showBackground = true)
@Composable
fun ActivitiesScreen(
    viewModel: ActivitiesViewModel,
    onActivityEditClick: (Activity) -> Unit,
    onActivityDeleteClick: (Activity) -> Unit,
    onActivityAddClick: () -> Unit
) {
    val activitiesList by viewModel.activitiesList.collectAsState()
    val loading by viewModel.loading.collectAsState()

    Column (
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "My Activities",
            style = MaterialTheme.typography.titleLarge,
            fontSize = 26.sp,
            modifier = Modifier.padding(bottom = 20.dp)
        )

        if (loading) {
            // Show loading indicator
            Column(
                modifier = Modifier
                    .fillMaxSize(0.8F)
                    .wrapContentHeight()
            ) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .size(40.dp)
                        .padding(top = 20.dp)
                        .align(Alignment.CenterHorizontally)
                )
                Spacer(modifier = Modifier.height(20.dp)) // Adjust the height as needed
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize(0.8F)
                    .padding(top = 30.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(activitiesList) { activity ->
                    ActivityItem(
                        activity = activity,
                        onActivityEditClick = onActivityEditClick,
                        onActivityDeleteClick = onActivityDeleteClick
                    )
                }
            }
        }

        Row (
            modifier = Modifier.fillMaxSize(0.4F),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            IconButton(
                onClick = { onActivityAddClick() },
                modifier = Modifier
                    .size(45.dp)
                    .background(MaterialTheme.colorScheme.surface)
                    .clip(CircleShape)
            ) {
                Icon(painter = painterResource(id = R.drawable.add_icon), contentDescription = "add activity icon")
            }
            
            IconButton(onClick = { viewModel.refreshActivities() },
                modifier = Modifier
                    .size(45.dp)
                    .clip(CircleShape)) {
                Icon(painter = painterResource(id = R.drawable.refresh_icon2), contentDescription = "refresh activities icon")
            }
        }
        

        if (connectionError.value != "") {
            Snackbar(
                modifier = Modifier
                    .padding(16.dp)
                    .height(80.dp),
                action = {
                    Button(
                        modifier = Modifier.padding(top = 10.dp),
                        onClick = {
                            connectionError.value = ""
                        }
                    ) {
                        Text(text = "Dismiss")
                    }
                }
            ) {
                Text(connectionError.value)
            }
        }
    }
}

@Composable
fun ActivityItem(
    activity: Activity,
    onActivityEditClick: (Activity) -> Unit,
    onActivityDeleteClick: (Activity) -> Unit
) {
    Row(modifier = Modifier.padding(all = 2.dp)) {
        val type = Types.typesList.find { it.name == activity.type }
        if (type != null) {
            Image(
                painter = painterResource(id = type.picture),
                contentDescription = null,
                modifier = Modifier
                    .size(34.dp)
                    .clip(CircleShape)
                    .border(1.dp, MaterialTheme.colorScheme.secondary, CircleShape)
            )
        }
        else {
            Image(
                painter = painterResource(id = R.drawable.other_stuff),
                contentDescription = null,
                modifier = Modifier
                    .size(34.dp)
                    .clip(CircleShape)
                    .border(1.dp, MaterialTheme.colorScheme.secondary, CircleShape)
            )
        }

        Spacer(modifier = Modifier.width(12.dp))

        var areDetailsDisplayed by remember { mutableStateOf(false) }
        // surfaceColor will be updated gradually from one color to the other
        val surfaceColor by animateColorAsState(
            if (areDetailsDisplayed) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surface,
            label = "",
        )

        Column(modifier = Modifier
            .width(180.dp)
            .clickable { areDetailsDisplayed = !areDetailsDisplayed }) {

            Surface(
                shape = MaterialTheme.shapes.medium,
                shadowElevation = 1.dp,
                color = surfaceColor,
                modifier = Modifier
                    .animateContentSize()
                    .padding(2.dp)
            ) {
                Text(
                    text = activity.toString(),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 10.dp),
                    // If the activity is expanded, we display all its content
                    // otherwise we only display the first line
                    maxLines = if (areDetailsDisplayed) Int.MAX_VALUE else 1,
                    style = MaterialTheme.typography.bodyMedium,
                    fontSize = 16.sp
                )
            }
        }
        Row (
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically
        ) {

            IconButton(
                onClick = { onActivityEditClick(activity) },
                modifier = Modifier
                    .padding(end = 15.dp)
                    .size(24.dp)
                    .clip(CircleShape),
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.edit_icon),
                    contentDescription = "edit activity icon",
                )
            }

            IconButton(
                onClick = { onActivityDeleteClick(activity) },
                modifier = Modifier
                    .padding(end = 5.dp)
                    .size(24.dp)
                    .clip(CircleShape),
                enabled = isConnectionOn
            ) {
                Image(painter = painterResource(id = R.drawable.delete_icon), contentDescription = "delete activity icon")
            }
        }
    }
}