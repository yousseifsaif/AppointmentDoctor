package com.example.registerapp.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.airbnb.lottie.LottieComposition
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.registerapp.R
import com.example.registerapp.viewmodel.DoctorViewModel

@Composable
fun DoctorDetailsScreen(
    doctorId: Int,
    doctorViewModel: DoctorViewModel = viewModel(),
    navController: NavController
) {
    val doctorLiveData = doctorViewModel.getDoctorByIdLive(doctorId)
    val doctor by doctorLiveData.observeAsState()
    val context = LocalContext.current

    var selectedDay by remember { mutableStateOf("") }
    var selectedTime by remember { mutableStateOf("") }
    var showSuccessDialog by remember { mutableStateOf(false) }

    val timeSlots = listOf(
        stringResource(R.string._10_00am),
        stringResource(R.string._12_00pm),
        stringResource(R.string._3_00pm),
        stringResource(R.string._5_00pm)
    )

    val successComposition by rememberLottieComposition(
        LottieCompositionSpec.RawRes(R.raw.success_animation)
    )

    doctor?.let { doc ->
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = stringResource(R.string.Bookanappointmentwith, doc.name),
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            Text(
                text = stringResource(R.string.specialty, doc.specialty),
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            SectionTitle(stringResource(R.string.choosetheday))

            LazyRow {
                items(doc.availableDays) { day ->
                    DayCard(day = day, isSelected = selectedDay == day) {
                        selectedDay = day
                        selectedTime = ""
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            if (selectedDay.isNotEmpty()) {
                SectionTitle(stringResource(R.string.choosethetime))

                LazyColumn {
                    items(timeSlots) { time ->
                        TimeRow(time = time, isSelected = selectedTime == time) {
                            selectedTime = time
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    doctorViewModel.bookAppointment(doc, selectedTime, selectedDay)
                    showSuccessDialog = true
                },
                enabled = selectedDay.isNotEmpty() && selectedTime.isNotEmpty(),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(stringResource(R.string.confirmappointment))
            }
        }
    }

    if (showSuccessDialog) {
        SuccessDialog(
            doctorName = doctor?.name.orEmpty(),
            selectedDay = selectedDay,
            selectedTime = selectedTime,
            composition = successComposition,
            onDismiss = {
                showSuccessDialog = false
                navController.popBackStack()
            }
        )
    }
}
@Composable
fun SectionTitle(text: String) {
    Text(
        text = text,
        style = MaterialTheme.typography.titleMedium,
        modifier = Modifier.padding(bottom = 8.dp)
    )
}

@Composable
fun DayCard(day: String, isSelected: Boolean, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .padding(4.dp)
            .clickable { onClick() },
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected)
                MaterialTheme.colorScheme.primary
            else
                MaterialTheme.colorScheme.surface
        )
    ) {
        Text(
            text = day,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(12.dp)
        )
    }
}

@Composable
fun TimeRow(time: String, isSelected: Boolean, onClick: () -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(8.dp)
    ) {
        RadioButton(
            selected = isSelected,
            onClick = { onClick() }
        )
        Text(
            text = time,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@Composable
fun SuccessDialog(
    doctorName: String,
    selectedDay: String,
    selectedTime: String,
    composition: LottieComposition?,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = { },
        confirmButton = {
            Button(
                onClick = onDismiss,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(stringResource(R.string.done))
            }
        },
        title = {
            Text(
                text = stringResource(R.string.Yourreservationhasbeencompletedsuccessfully),
                modifier = Modifier.fillMaxWidth(),
                style = MaterialTheme.typography.headlineSmall
            )
        },
        text = {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                if (composition != null) {
                    LottieAnimation(
                        composition = composition,
                        iterations = 1,
                        modifier = Modifier.size(120.dp)
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = stringResource(R.string.Yourappointmenthasbeensuccessfullyconfirmed),
                    style = MaterialTheme.typography.bodyLarge,
                    textAlign = TextAlign.Center
                )
                Text(
                    text = "Doctor: $doctorName",
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Start
                )
                Text(
                    text = stringResource(R.string.time1, selectedDay, selectedTime),
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Start
                )
            }
        }
    )
}
