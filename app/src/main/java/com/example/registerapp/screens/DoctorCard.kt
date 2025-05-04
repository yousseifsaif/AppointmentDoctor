package com.example.registerapp.screens


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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.registerapp.R
import com.example.registerapp.doctorDatabase.Doctor

@Composable
fun DoctorCard(
    doctor: Doctor,
    modifier: Modifier = Modifier,
    onBookClick: (Doctor) -> Unit
) {
    Card(
        modifier = modifier
            .padding(12.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            DoctorImage()

            VerticalSpacer(8)

            DoctorName(name = doctor.name)
            DoctorSpecialty(specialty = doctor.specialty)
            DoctorAvailableDays(days = doctor.availableDays)

            VerticalSpacer(12)

            BookButton {
                onBookClick(doctor)
            }
        }
    }
}

@Composable
fun DoctorImage() {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.doc),
            contentDescription = stringResource(R.string.docpic),
            modifier = Modifier
                .size(100.dp)
                .clip(RoundedCornerShape(50))
        )
    }
}

@Composable
fun DoctorName(name: String) {
    Text(
        text = name,
        style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
        color = MaterialTheme.colorScheme.onSurface,
        textAlign = TextAlign.Center,
        modifier = Modifier.fillMaxWidth()
    )
}

@Composable
fun DoctorSpecialty(specialty: String) {
    VerticalSpacer(4)
    Text(
        text = stringResource(R.string.specialty, specialty),
        style = MaterialTheme.typography.bodyMedium,
        color = MaterialTheme.colorScheme.onSurface,
        modifier = Modifier.fillMaxWidth()
    )
}

@Composable
fun DoctorAvailableDays(days: List<String>) {
    VerticalSpacer(4)
    Text(
        text = stringResource(R.string.Availableappointments, days.joinToString(", ")),
        style = MaterialTheme.typography.bodySmall,
        color = MaterialTheme.colorScheme.onSurface,
        modifier = Modifier.fillMaxWidth()
    )
}

@Composable
fun BookButton(onClick: () -> Unit) {
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(12.dp),
        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
        modifier = Modifier.fillMaxWidth()
    ) {
        Icon(Icons.Default.DateRange, contentDescription = null)
        Spacer(Modifier.width(8.dp))
        Text(stringResource(R.string.booknow))
    }
}

@Composable
fun VerticalSpacer(height: Int) {
    Spacer(modifier = Modifier.height(height.dp))
}
