package com.example.registerapp.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.registerapp.R
import com.example.registerapp.viewmodel.DoctorViewModel

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
            text = stringResource(R.string.availabledoctors),
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(16.dp))

        if (doctors.isEmpty()) {
            Text(
                text = stringResource(R.string.Therearenodoctorscurrentlyavailable),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        } else {
            LazyColumn {
                items(doctors) { doctor ->
                    DoctorCard(doctor = doctor) {
                        navController.navigate("booking/${it.id}")
                    }
                }
            }
        }
    }
}
