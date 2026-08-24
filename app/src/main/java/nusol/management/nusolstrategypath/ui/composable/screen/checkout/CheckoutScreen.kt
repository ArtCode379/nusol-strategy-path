package nusol.management.nusolstrategypath.ui.composable.screen.checkout

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import nusol.management.nusolstrategypath.data.entity.BookingEntity
import nusol.management.nusolstrategypath.data.repository.ServiceRepository
import nusol.management.nusolstrategypath.ui.state.DataUiState
import nusol.management.nusolstrategypath.ui.viewmodel.CheckoutViewModel
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.koinInject

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CheckoutScreen(
    serviceId: Int,
    modifier: Modifier = Modifier,
    viewModel: CheckoutViewModel = koinViewModel(),
    onNavigateToBookingsScreen: () -> Unit,
) {
    val repository: ServiceRepository = koinInject()
    val service = remember(serviceId) { repository.getById(serviceId) }
    val bookingState by viewModel.orderState.collectAsStateWithLifecycle()
    val invalidEmail by viewModel.emailInvalidState.collectAsStateWithLifecycle()
    var phone by remember { mutableStateOf("") }
    var selectedDate by remember { mutableStateOf("") }
    var notes by remember { mutableStateOf("") }
    var showDatePicker by remember { mutableStateOf(false) }
    val datePickerState = rememberDatePickerState()
    val isComplete = viewModel.customerFirstName.isNotBlank() && viewModel.customerLastName.isNotBlank() && viewModel.customerEmail.isNotBlank() && phone.isNotBlank() && selectedDate.isNotBlank()

    if (bookingState is DataUiState.Populated) {
        CheckoutDialog((bookingState as DataUiState.Populated<BookingEntity>).data, onNavigateToBookingsScreen)
    }

    Column(
        modifier = modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp),
    ) {
        Text("Reserve your session", style = MaterialTheme.typography.headlineMedium)
        Card(modifier = Modifier.fillMaxWidth()) {
            Column(Modifier.padding(16.dp)) {
                Text(service?.name.orEmpty(), style = MaterialTheme.typography.titleMedium)
                Text("From $${service?.price?.toInt() ?: 0} · ${service?.durationMinutes ?: 0} min", color = MaterialTheme.colorScheme.primary)
            }
        }
        OutlinedTextField(viewModel.customerFirstName, viewModel::updateCustomerFirstName, label = { Text("First name") }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(viewModel.customerLastName, viewModel::updateCustomerLastName, label = { Text("Last name") }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(viewModel.customerEmail, viewModel::updateCustomerEmail, label = { Text("Email") }, isError = invalidEmail, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(phone, { phone = it }, label = { Text("Phone") }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(
            value = selectedDate,
            onValueChange = { selectedDate = it },
            readOnly = true,
            label = { Text("Preferred date") },
            trailingIcon = { Icon(Icons.Default.CalendarMonth, contentDescription = null) },
            modifier = Modifier.fillMaxWidth().clickable { showDatePicker = true },
        )
        OutlinedTextField(notes, { notes = it }, label = { Text("Notes for your consultant") }, minLines = 3, modifier = Modifier.fillMaxWidth())
        Button(onClick = { viewModel.placeBooking(serviceId) }, enabled = isComplete, modifier = Modifier.fillMaxWidth()) {
            Text("Confirm Booking")
        }
    }

    if (showDatePicker) {
        DatePickerDialog(
            onDismissRequest = { showDatePicker = false },
            confirmButton = {
                TextButton(onClick = {
                    datePickerState.selectedDateMillis?.let { millis ->
                        selectedDate = Instant.ofEpochMilli(millis).atZone(ZoneId.systemDefault()).toLocalDate().format(DateTimeFormatter.ofPattern("MMM dd, yyyy"))
                    }
                    showDatePicker = false
                }) { Text("Select") }
            },
            dismissButton = { TextButton(onClick = { showDatePicker = false }) { Text("Cancel") } },
        ) { DatePicker(state = datePickerState) }
    }
}
