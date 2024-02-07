package com.example.activityplannerapp.activities.screens

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.util.Log
import android.widget.DatePicker
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.activityplannerapp.R
import com.example.activityplannerapp.activities.domain.Activity
import com.example.activityplannerapp.activities.domain.Types
import com.example.activityplannerapp.activities.viewmodel.ActivitiesViewModel
import com.example.activityplannerapp.activities.viewmodel.AddActivityViewModel
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Date

//@Preview(showBackground = true)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddActivityScreen(
    viewModel: AddActivityViewModel,
    activitiesViewModel: ActivitiesViewModel,
    onAdd: (Activity) -> Unit,
    onCancel: () -> Unit
) {
    val activity = viewModel.activity
    val activitiesList by activitiesViewModel.activitiesList.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 40.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        var selectedType by remember { mutableStateOf(Types.typesList[0]) }

        TextField(
            value = activity.value.title,
            onValueChange = { newTitle ->
                activity.value = activity.value.copy(title = newTitle)
            },
            label = { Text("Title") }
        )


        val typesList = Types.typesList
        val isDropdownExpanded = remember { mutableStateOf(false) }

        Row {
            Text(text = "Type: ", modifier = Modifier.padding(top = 10.dp), fontSize = 18.sp)
            Spacer(modifier = Modifier.width(10.dp))

            Box(
                contentAlignment = Alignment.CenterStart,
                modifier = Modifier
                    .clickable { isDropdownExpanded.value = true }

            ) {
                Text(text = selectedType.name, fontSize = 18.sp, modifier = Modifier.padding(top = 10.dp))
                DropdownMenu(
                    expanded = isDropdownExpanded.value,
                    onDismissRequest = { isDropdownExpanded.value = false }
                ) {
                    typesList.forEach { type ->
                        DropdownMenuItem(
                            onClick = {
                                selectedType = type
                                isDropdownExpanded.value = false
                            },
                            text = { Text(text = type.name, fontSize = 16.sp) }
                        )
                    }
                }
            }
        }

        activity.value = activity.value.copy(type = selectedType.name)

        val mContext = LocalContext.current
        // Declaring and initializing a calendar
        val mCalendar = Calendar.getInstance()
        mCalendar.time = Date()

        var selectedDate by remember { mutableStateOf(LocalDate.now()) }
        var selectedStartTime by remember { mutableStateOf(LocalTime.now()) }
        var selectedEndTime by remember { mutableStateOf(LocalTime.now()) }

        // Function to update the selected date
        fun updateSelectedDate(year: Int, month: Int, dayOfMonth: Int) {
            selectedDate = LocalDate.of(year, month + 1, dayOfMonth)
        }

        fun updateSelectedStartTime(hour: Int, minute: Int) {
            selectedStartTime = LocalTime.of(hour, minute)
        }

        fun updateSelectedEndTime(hour: Int, minute: Int) {
            selectedEndTime = LocalTime.of(hour, minute)
        }

        fun showDatePicker(onDateSelected: (Int, Int, Int) -> Unit) {
            val mYear = mCalendar.get(Calendar.YEAR)
            val mMonth = mCalendar.get(Calendar.MONTH)
            val mDay = mCalendar.get(Calendar.DAY_OF_MONTH)

            val mDatePickerDialog = DatePickerDialog(
                mContext,
                { _, year, month, dayOfMonth ->
                    // Handle date selection
                    onDateSelected(year, month, dayOfMonth)
                    activity.value = activity.value.copy(date = LocalDate.of(year, month + 1, dayOfMonth))
                },
                mYear, mMonth, mDay
            )

            mDatePickerDialog.show()
        }

        fun showTimePickerForStart(updateSelectedTime: (Int, Int) -> Unit) {
            val mHour = mCalendar.get(Calendar.HOUR_OF_DAY)
            val mMinute = mCalendar.get(Calendar.MINUTE)

            val mTimePickerDialog = TimePickerDialog(
                mContext,
                { _, hour, minute ->
                    // Handle time selection
                    updateSelectedTime(hour, minute)
                    activity.value = activity.value.copy(startTime = LocalTime.of(hour, minute))
                },
                mHour, mMinute, false
            )

            mTimePickerDialog.show()
        }

        fun showTimePickerForEnd(updateSelectedTime: (Int, Int) -> Unit) {
            val mHour = mCalendar.get(Calendar.HOUR_OF_DAY)
            val mMinute = mCalendar.get(Calendar.MINUTE)

            val mTimePickerDialog = TimePickerDialog(
                mContext,
                { _, hour, minute ->
                    // Handle time selection
                    updateSelectedTime(hour, minute)
                    activity.value = activity.value.copy(endTime = LocalTime.of(hour, minute))
                },
                mHour, mMinute, false
            )

            mTimePickerDialog.show()
        }

        // Declaring DatePickerDialog and setting
        // initial values as current values (present year, month and day)

        Row(modifier = Modifier.padding(top = 15.dp)) {

            Text(text = "Date: ", fontSize = 18.sp)

            Spacer(modifier = Modifier.width(15.dp))

            IconButton(onClick = { showDatePicker { year, month, day -> updateSelectedDate(year, month, day) } }) {
                Icon(
                    painter = painterResource(id = R.drawable.calendar_icon),
                    contentDescription = "calendar icon",
                    tint = Color.Unspecified
                )
            }

            Spacer(modifier = Modifier.width(10.dp))

            Text(
                text = selectedDate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")),
                fontSize = 18.sp
            )

        }

        Row(modifier = Modifier.padding(top = 15.dp)) {
            Text(
                text = "Start Time: ",
                fontSize = 18.sp
            )

            Spacer(modifier = Modifier.width(15.dp))

            IconButton(onClick = {
                showTimePickerForStart { hour, minute ->
                    updateSelectedStartTime(
                        hour,
                        minute
                    )
                }
            }) {
                Icon(
                    painter = painterResource(id = R.drawable.clock_icon),
                    contentDescription = "clock icon",
                    tint = Color.Unspecified
                )
            }

            Spacer(modifier = Modifier.width(10.dp))

            Text(
                text = selectedStartTime.format(DateTimeFormatter.ofPattern("HH:mm")),
                fontSize = 18.sp
            )
        }


        Row(modifier = Modifier.padding(top = 15.dp),) {
            Text(
                text = "End Time: ",
                fontSize = 18.sp
            )

            Spacer(modifier = Modifier.width(15.dp))

            IconButton(onClick = {
                showTimePickerForEnd { hour, minute ->
                    updateSelectedEndTime(
                        hour,
                        minute
                    )
                }
            }) {
                Icon(
                    painter = painterResource(id = R.drawable.clock_icon),
                    contentDescription = "clock icon",
                    tint = Color.Unspecified
                )
            }

            Spacer(modifier = Modifier.width(10.dp))

            Text(
                text = selectedEndTime.format(DateTimeFormatter.ofPattern("HH:mm")),
                fontSize = 18.sp
            )
        }

        TextField(
            value = activity.value.description,
            onValueChange = { newDescription ->
                activity.value = activity.value.copy(description = newDescription)
            },
            label = { Text("Description") },
            modifier = Modifier
                .height(100.dp)
                .padding(top = 15.dp)
        )

        val activityWasSubmitted = remember { mutableStateOf(false) }

        Row( modifier = Modifier
            .padding(top = 50.dp)
            .fillMaxSize(0.7F),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(
                onClick = {
                    val overlappingActivity = checkForOverlap(activity.value, activitiesList)
                    if (overlappingActivity == null)
                        onAdd(activity.value)
                    activityWasSubmitted.value = true },
            ) {
                Text("Confirm")
            }

            Button(onClick = { onCancel() }) {
                Text("Cancel")
            }
        }

        val errorMessage = viewModel.errorMessage.value
        if (errorMessage != "") {
            Snackbar(
                modifier = Modifier.padding(16.dp).height(80.dp),
                action = {
                    Button(
                        onClick = {
                            viewModel.errorMessage.value = ""
                            activityWasSubmitted.value = false
                        }
                    ) {
                        Text(text = "Dismiss")
                    }
                }
            ) {
                Text(errorMessage)
            }
        }
        else if (activityWasSubmitted.value) {
            val overlappingActivity = checkForOverlap(activity.value, activitiesList)

            if (overlappingActivity != null) {
                viewModel.errorMessage.value = "Activity overlaps with an existing activity"
                Snackbar(
                    modifier = Modifier.padding(16.dp).height(80.dp),
                    action = {
                        Button(
                            onClick = {
                                viewModel.errorMessage.value = ""
                                activityWasSubmitted.value = false
                            }
                        ) {
                            Text(text = "Dismiss")
                        }
                    }
                ) {
                    Text(errorMessage)
                }
            }
        }

    }

}

fun checkForOverlap(newActivity: Activity, activities: List<Activity>): Activity? {
    for (activity in activities) {
        if (newActivity.date == activity.date && newActivity.id != activity.id) {
            // Check if the new activity's startTime and endTime overlap with an existing activity
            if (newActivity.startTime.isBefore(activity.endTime) && newActivity.endTime.isAfter(activity.startTime)) {
                return activity // Return the overlapping activity
            }
        }
    }
    return null // No overlap
}
