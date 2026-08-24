package nusol.management.nusolstrategypath.ui.composable.screen.servicedetails

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import nusol.management.nusolstrategypath.data.model.ServiceModel
import nusol.management.nusolstrategypath.ui.state.DataUiState
import nusol.management.nusolstrategypath.ui.viewmodel.ServiceDetailsViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun ServiceDetailsScreen(
    serviceId: Int,
    modifier: Modifier = Modifier,
    viewModel: ServiceDetailsViewModel = koinViewModel(),
    onNavigateToCheckout: (serviceId: Int) -> Unit,
) {
    val state by viewModel.serviceState.collectAsState()
    LaunchedEffect(serviceId) { viewModel.observeServiceById(serviceId) }
    val service = (state as? DataUiState.Populated)?.data
    if (service != null) {
        Details(service, modifier) { onNavigateToCheckout(service.id) }
    }
}

@Composable
private fun Details(service: ServiceModel, modifier: Modifier, onBook: () -> Unit) {
    LazyColumn(modifier = modifier.fillMaxSize(), verticalArrangement = Arrangement.spacedBy(18.dp)) {
        item {
            AsyncImage(
                model = service.imageUrl,
                contentDescription = service.name,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxWidth().height(280.dp).clip(RoundedCornerShape(bottomStart = 28.dp, bottomEnd = 28.dp)),
            )
        }
        item {
            Column(Modifier.padding(horizontal = 20.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Surface(color = MaterialTheme.colorScheme.primaryContainer, shape = RoundedCornerShape(50)) {
                    Text(service.category, modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp), color = MaterialTheme.colorScheme.primary)
                }
                Text(service.name, style = MaterialTheme.typography.titleLarge)
                Text("From $${service.price.toInt()}  ·  ${service.durationMinutes} min", style = MaterialTheme.typography.titleMedium, color = MaterialTheme.colorScheme.primary)
                Text(service.description, style = MaterialTheme.typography.bodyLarge)
            }
        }
        item {
            Text("What is included", style = MaterialTheme.typography.titleLarge, modifier = Modifier.padding(horizontal = 20.dp))
        }
        items(service.features) { feature ->
            Row(modifier = Modifier.padding(horizontal = 20.dp), horizontalArrangement = Arrangement.spacedBy(12.dp), verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.CheckCircle, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
                Text(feature, style = MaterialTheme.typography.bodyLarge)
            }
        }
        item {
            Text("Available times", style = MaterialTheme.typography.titleLarge, modifier = Modifier.padding(horizontal = 20.dp))
            LazyRow(contentPadding = androidx.compose.foundation.layout.PaddingValues(20.dp), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                items(service.availableTime.orEmpty()) { time ->
                    Card { Text(time.toString(), modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp)) }
                }
            }
        }
        item {
            Button(onClick = onBook, modifier = Modifier.padding(20.dp).fillMaxWidth()) {
                Text("Book Consultation")
            }
        }
    }
}
