package nusol.management.nusolstrategypath.ui.composable.screen.checkout

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import nusol.management.nusolstrategypath.data.entity.BookingEntity

@Composable
fun CheckoutDialog(booking: BookingEntity, onConfirm: () -> Unit) {
    AlertDialog(
        onDismissRequest = onConfirm,
        title = { Text("Consultation confirmed") },
        text = {
            Text("Session #${booking.bookingNumber} is reserved. Your consultant will be waiting in the online conference or at the office at the appointed time.")
        },
        confirmButton = { TextButton(onClick = onConfirm) { Text("View bookings") } },
    )
}
