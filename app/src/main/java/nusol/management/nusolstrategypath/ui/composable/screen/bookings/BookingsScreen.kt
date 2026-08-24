package nusol.management.nusolstrategypath.ui.composable.screen.bookings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import nusol.management.nusolstrategypath.ui.state.BookingUiState
import nusol.management.nusolstrategypath.ui.state.DataUiState
import nusol.management.nusolstrategypath.ui.theme.Success
import nusol.management.nusolstrategypath.ui.viewmodel.BookingViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun BookingsScreen(
    modifier: Modifier = Modifier,
    viewModel: BookingViewModel = koinViewModel(),
) {
    val state by viewModel.bookingsState.collectAsState()
    var pendingCancellation by remember { mutableStateOf<String?>(null) }
    val bookings = (state as? DataUiState.Populated)?.data.orEmpty()

    if (bookings.isEmpty()) {
        Column(
            modifier = modifier.fillMaxSize().padding(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Text("No bookings yet", style = MaterialTheme.typography.headlineMedium)
            Text("Browse Services from Home to plan your first consultation.", color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
    } else {
        LazyColumn(modifier = modifier.fillMaxSize(), contentPadding = androidx.compose.foundation.layout.PaddingValues(20.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
            item { Text("Your sessions", style = MaterialTheme.typography.headlineMedium) }
            items(bookings, key = { it.bookingNumber }) { booking ->
                BookingCard(booking) { pendingCancellation = booking.bookingNumber }
            }
        }
    }

    pendingCancellation?.let { bookingNumber ->
        AlertDialog(
            onDismissRequest = { pendingCancellation = null },
            title = { Text("Cancel this booking?") },
            text = { Text("The reserved consultation slot will be released.") },
            confirmButton = {
                TextButton(
                    onClick = {
                        viewModel.cancelBooking(bookingNumber)
                        pendingCancellation = null
                    },
                ) { Text("Confirm", color = MaterialTheme.colorScheme.error) }
            },
            dismissButton = { TextButton(onClick = { pendingCancellation = null }) { Text("Keep booking") } },
        )
    }
}

@Composable
private fun BookingCard(booking: BookingUiState, onCancel: () -> Unit) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(Modifier.padding(18.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text(booking.serviceName, style = MaterialTheme.typography.titleMedium, modifier = Modifier.weight(1f))
                Surface(color = Success.copy(alpha = 0.14f), shape = RoundedCornerShape(50)) {
                    Text("Confirmed", color = Success, modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp))
                }
            }
            Text(booking.timestamp, color = MaterialTheme.colorScheme.onSurfaceVariant)
            Text("Session #${booking.bookingNumber}", style = MaterialTheme.typography.labelLarge)
            TextButton(onClick = onCancel, modifier = Modifier.align(Alignment.End)) {
                Text("Cancel", color = MaterialTheme.colorScheme.error)
            }
        }
    }
}
