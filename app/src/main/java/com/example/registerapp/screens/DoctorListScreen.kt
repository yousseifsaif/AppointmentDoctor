package com.example.registerapp.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.registerapp.viewmodel.DoctorViewModel
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.registerapp.R
import com.example.registerapp.doctorDatabase.Doctor
import androidx.compose.ui.text.style.TextAlign


@Composable
fun DoctorListScreen(
    navController: NavHostController,
    doctorViewModel: DoctorViewModel,
    onLogout: () -> Unit
) {
    val doctors by doctorViewModel.allDoctors.observeAsState(emptyList())

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
            Button(onClick = onLogout) {
                Text(stringResource(R.string.logout))
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            stringResource(R.string.availabledoctors), style = MaterialTheme.typography.headlineMedium, // محاذاة لليمين
        )


        Spacer(modifier = Modifier.height(16.dp))

        if (doctors.isEmpty()) {
            Text(stringResource(R.string.Therearenodoctorscurrentlyavailable), style = MaterialTheme.typography.bodyMedium, color = Color.Gray)
        } else {
            LazyColumn {
                items(doctors) { doctor ->
                    DoctorCard(doctor = doctor, onBookClick = {
                        navController.navigate("booking/${doctor.id}")
                    })
                }
            }
        }
    }
}

@Composable
fun DoctorCard(
    doctor: Doctor,
    onBookClick: (Doctor) -> Unit
) {
    Card(
        modifier = Modifier
            .padding(12.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Image(
                painter = painterResource(id = R.drawable.doc),
                contentDescription = stringResource(R.string.docpic),
                modifier = Modifier
                    .size(100.dp)
                    .clip(RoundedCornerShape(50))
                    .align(Alignment.CenterHorizontally)
            )

            Spacer(modifier = Modifier.height(8.dp))



            Text(
                text = doctor.name,
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                color = MaterialTheme.colorScheme.onSurface,
                        textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = stringResource(R.string.specialty, doctor.specialty),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface,
                textAlign = TextAlign.Start,
                modifier = Modifier.fillMaxWidth()

            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = stringResource(
                    R.string.Availableappointments,
                    doctor.availableDays.joinToString(", ")
                ),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurface,
                textAlign = TextAlign.Start,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(12.dp))

            Button(
                onClick = { onBookClick(doctor) },
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary
                ),
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(Icons.Default.DateRange, contentDescription = null)
                Spacer(Modifier.width(8.dp))
                Text(stringResource(R.string.booknow))
            }
        }
    }
}