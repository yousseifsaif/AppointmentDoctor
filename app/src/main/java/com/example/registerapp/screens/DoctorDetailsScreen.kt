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

    val timeSlots = listOf(stringResource(R.string._10_00am),
        stringResource(R.string._12_00pm), stringResource(R.string._3_00pm),
        stringResource(R.string._5_00pm)
    )

    val successComposition by rememberLottieComposition(
        LottieCompositionSpec.RawRes(com.example.registerapp.R.raw.success_animation)
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

            Text(
                text = stringResource(R.string.choosetheday),
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            LazyRow {
                items(doc.availableDays) { day ->
                    Card(
                        modifier = Modifier
                            .padding(4.dp)
                            .clickable {
                                selectedDay = day
                                selectedTime = ""
                            },
                        colors = CardDefaults.cardColors(
                            containerColor = if (selectedDay == day)
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
            }

            Spacer(modifier = Modifier.height(16.dp))

            if (selectedDay.isNotEmpty()) {
                Text(
                    text = stringResource(R.string.choosethetime),
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                LazyColumn {
                    items(timeSlots) { time ->
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { selectedTime = time }
                                .padding(8.dp)
                        ) {
                            RadioButton(
                                selected = selectedTime == time,
                                onClick = { selectedTime = time }
                            )
                            Text(
                                text = time,
                                style = MaterialTheme.typography.bodyMedium
                            )
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
        AlertDialog(
            onDismissRequest = { showSuccessDialog = false },
            confirmButton = {
                Button(
                    onClick = {
                        showSuccessDialog = false
                        navController.popBackStack()
                    },
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
                    LottieAnimation(
                        composition = successComposition,
                        iterations = 1,
                        modifier = Modifier.size(120.dp)
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = stringResource(R.string.Yourappointmenthasbeensuccessfullyconfirmed),
                        style = MaterialTheme.typography.bodyLarge,
                        textAlign = TextAlign.Center,
                    )
                    Text(
                        text = "Doctor: ${doctor?.name}",
                        style = MaterialTheme.typography.bodyMedium,
                        textAlign = TextAlign.Start,
                    )
                    Text(
                        text = stringResource(R.string.time1, selectedDay, selectedTime),
                        style = MaterialTheme.typography.bodyMedium,
                        textAlign = TextAlign.Start,
                    )
                }
            }
        )
    }
}