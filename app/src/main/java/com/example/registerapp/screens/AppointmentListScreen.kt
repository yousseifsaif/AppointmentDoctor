package com.example.registerapp.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.registerapp.R
import com.example.registerapp.doctorDatabase.Appointment
import com.example.registerapp.viewmodel.AppointmentViewModel

@Composable
fun AppointmentListScreen(viewModel: AppointmentViewModel) {
    val appointments = viewModel.appointments.observeAsState(emptyList()).value

    BaseScaffoldContent(
        dataIsEmpty = appointments.isEmpty(),
        emptyText = stringResource(R.string.Noappointmentsbooked),
        content = {
            LazyColumn(
                contentPadding = PaddingValues(16.dp),
                modifier = Modifier.padding(it)
            ) {
                items(appointments) { appointment ->
                    AppointmentCard(appointment = appointment) {
                        viewModel.deleteAppointment(appointment)
                    }
                    VerticalSpacer(8.dp)
                }
            }
        }
    )
}

@Composable
fun AppointmentCard(appointment: Appointment, onDelete: () -> Unit) {
    BaseCard {
        Column(modifier = Modifier.padding(16.dp)) {
            BoldText(stringResource(R.string.doctor, appointment.doctorName))
            RegularText(stringResource(R.string.specialty, appointment.specialty))
            RegularText(stringResource(R.string.time, appointment.time))
            ColoredText(
                text = stringResource(R.string.day, appointment.day),
                color = Color(0xFF3F51B5),
                weight = FontWeight.Medium
            )
            VerticalSpacer(8.dp)
            BaseButton(
                text = stringResource(R.string.Cancelappointment),
                onClick = onDelete,
                buttonColor = Color.Red,
                textColor = Color.White
            )
        }
    }
}

@Composable
fun BaseScaffoldContent(
    dataIsEmpty: Boolean,
    emptyText: String,
    content: @Composable (PaddingValues) -> Unit
) {
    Scaffold(topBar = {}) { padding ->
        if (dataIsEmpty) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentAlignment = Alignment.Center
            ) {
                Text(emptyText)
            }
        } else {
            content(padding)
        }
    }
}
@Composable
fun BaseCard(content: @Composable () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        content()
    }
}

@Composable
fun BaseButton(
    text: String,
    onClick: () -> Unit,
    buttonColor: Color = MaterialTheme.colorScheme.primary,
    textColor: Color = Color.White
) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(containerColor = buttonColor)
    ) {
        Text(text, color = textColor)
    }
}

@Composable
fun BoldText(text: String) {
    Text(text = text, fontWeight = FontWeight.Bold)
}

@Composable
fun RegularText(text: String) {
    Text(text = text)
}

@Composable
fun ColoredText(text: String, color: Color, weight: FontWeight = FontWeight.Normal) {
    Text(text = text, color = color, fontWeight = weight)
}

@Composable
fun VerticalSpacer(height: Dp) {
    Spacer(modifier = Modifier.height(height))
}
