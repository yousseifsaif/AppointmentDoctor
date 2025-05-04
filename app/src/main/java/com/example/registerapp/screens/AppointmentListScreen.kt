package com.example.registerapp.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.registerapp.doctorDatabase.Appointment
import com.example.registerapp.viewmodel.AppointmentViewModel
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.res.stringResource
import com.example.registerapp.R


@Composable
fun AppointmentListScreen(viewModel: AppointmentViewModel) {
    val appointments by viewModel.appointments.observeAsState(emptyList())

    Scaffold(


        topBar = {
        }
    )

    { padding ->
        if (appointments.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentAlignment = Alignment.Center
            ) {
                Text(stringResource(R.string.Noappointmentsbooked))
            }
        } else {
            LazyColumn(
                contentPadding = PaddingValues(16.dp),
                modifier = Modifier.padding(padding)
            ) {
                items(appointments) { appointment ->
                    AppointmentCard(appointment = appointment) {
                        viewModel.deleteAppointment(appointment)
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
    }
}

@Composable
fun AppointmentCard(appointment: Appointment, onDelete: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(stringResource(R.string.doctor, appointment.doctorName), fontWeight = FontWeight.Bold)
            Text(stringResource(R.string.specialty, appointment.specialty))
            Text(stringResource(R.string.time, appointment.time))
            Text(
                text = stringResource(R.string.day, appointment.day),
                fontWeight = FontWeight.Medium,
                color = Color(0xFF3F51B5)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Button(
                onClick = onDelete,
                colors = ButtonDefaults.buttonColors(Color.Red)
            ) {
                Text(stringResource(R.string.Cancelappointment), color = Color.White)
            }
        }
    }
}

