package nusol.management.nusolstrategypath.ui.composable.screen.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.SettingsSuggest
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import nusol.management.nusolstrategypath.data.model.ServiceModel
import nusol.management.nusolstrategypath.ui.state.DataUiState
import nusol.management.nusolstrategypath.ui.theme.StrategyBlueDark
import nusol.management.nusolstrategypath.ui.viewmodel.ServiceViewModel
import org.koin.androidx.compose.koinViewModel

private val categories = listOf(
    "Strategy" to Icons.Default.TrendingUp,
    "Organization" to Icons.Default.Groups,
    "Operations" to Icons.Default.SettingsSuggest,
    "Change" to Icons.Default.Lightbulb,
)

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: ServiceViewModel = koinViewModel(),
    onNavigateToServiceDetails: (serviceId: Int) -> Unit,
) {
    val state by viewModel.servicesState.collectAsState()
    val services = (state as? DataUiState.Populated)?.data.orEmpty()

    LazyColumn(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(14.dp),
    ) {
        item {
            Column(Modifier.padding(horizontal = 20.dp, vertical = 12.dp)) {
                Text("Better decisions start with clarity", style = MaterialTheme.typography.headlineMedium)
                Text("Management consulting built around your next critical move.", color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
        }
        if (services.isNotEmpty()) {
            item {
                FeaturedCard(services.first(), onNavigateToServiceDetails)
            }
        }
        item {
            Text("Explore by focus", style = MaterialTheme.typography.titleLarge, modifier = Modifier.padding(horizontal = 20.dp))
        }
        item {
            LazyRow(
                contentPadding = androidx.compose.foundation.layout.PaddingValues(horizontal = 20.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                items(categories) { category -> CategoryCard(category.first, category.second) }
            }
        }
        item {
            Text("Consulting solutions", style = MaterialTheme.typography.titleLarge, modifier = Modifier.padding(horizontal = 20.dp))
        }
        items(services, key = { it.id }) { service ->
            ServiceCard(service, onNavigateToServiceDetails)
        }
        item {
            Text("Transformation portfolio", style = MaterialTheme.typography.titleLarge, modifier = Modifier.padding(horizontal = 20.dp, vertical = 8.dp))
        }
        items(services.takeLast(3), key = { "portfolio-${it.id}" }) { service ->
            InsightCard(
                eyebrow = "CLIENT OUTCOME",
                title = when (service.id) {
                    8 -> "A clearer operating model cut decision delays by 35%"
                    9 -> "Redesigned journeys lifted service satisfaction by 22%"
                    else -> "A sequenced roadmap brought 14 initiatives under one plan"
                },
                serviceId = service.id,
                onClick = onNavigateToServiceDetails,
            )
        }
        item {
            Text("Knowledge base", style = MaterialTheme.typography.titleLarge, modifier = Modifier.padding(horizontal = 20.dp, vertical = 8.dp))
        }
        items(
            listOf(
                Triple(1, "Strategy", "Five questions that turn ambition into an executable strategy"),
                Triple(5, "Leadership", "How strong leadership teams make difficult decisions faster"),
                Triple(6, "Change", "Building change readiness before transformation begins"),
            ),
        ) { article ->
            InsightCard(article.second.uppercase(), article.third, article.first, onNavigateToServiceDetails)
        }
    }
}

@Composable
private fun InsightCard(eyebrow: String, title: String, serviceId: Int, onClick: (Int) -> Unit) {
    Card(modifier = Modifier.padding(horizontal = 20.dp).fillMaxWidth().clickable { onClick(serviceId) }) {
        Column(Modifier.padding(18.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Text(eyebrow, color = MaterialTheme.colorScheme.primary, style = MaterialTheme.typography.labelLarge)
            Text(title, style = MaterialTheme.typography.titleMedium)
            Text("Read insight →", color = MaterialTheme.colorScheme.onSurfaceVariant, style = MaterialTheme.typography.bodyMedium)
        }
    }
}

@Composable
private fun FeaturedCard(service: ServiceModel, onClick: (Int) -> Unit) {
    Box(
        modifier = Modifier
            .padding(horizontal = 20.dp)
            .fillMaxWidth()
            .height(205.dp)
            .clip(RoundedCornerShape(20.dp))
            .clickable { onClick(service.id) },
    ) {
        AsyncImage(model = service.imageUrl, contentDescription = null, contentScale = ContentScale.Crop, modifier = Modifier.fillMaxSize())
        Surface(color = Color.Transparent, modifier = Modifier.fillMaxSize()) {
            Box(modifier = Modifier.fillMaxSize().clip(RoundedCornerShape(20.dp))) {
                androidx.compose.foundation.layout.Spacer(Modifier.fillMaxSize().background(Brush.verticalGradient(listOf(Color.Transparent, StrategyBlueDark.copy(alpha = 0.92f)))))
                Column(modifier = Modifier.align(Alignment.BottomStart).padding(20.dp)) {
                    Text("NEXT AVAILABLE · TOMORROW 9:00 AM", color = MaterialTheme.colorScheme.secondary, style = MaterialTheme.typography.labelLarge)
                    Text(service.name, color = Color.White, style = MaterialTheme.typography.titleLarge)
                }
            }
        }
    }
}

@Composable
private fun CategoryCard(label: String, icon: ImageVector) {
    Card(colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)) {
        Column(
            modifier = Modifier.padding(16.dp).size(width = 92.dp, height = 78.dp),
            verticalArrangement = Arrangement.SpaceBetween,
        ) {
            Icon(icon, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
            Text(label, style = MaterialTheme.typography.labelLarge)
        }
    }
}

@Composable
private fun ServiceCard(service: ServiceModel, onClick: (Int) -> Unit) {
    Card(
        modifier = Modifier.padding(horizontal = 20.dp).fillMaxWidth().clickable { onClick(service.id) },
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            AsyncImage(model = service.imageUrl, contentDescription = null, contentScale = ContentScale.Crop, modifier = Modifier.size(116.dp))
            Column(modifier = Modifier.padding(14.dp).weight(1f), verticalArrangement = Arrangement.spacedBy(5.dp)) {
                Text(service.category, color = MaterialTheme.colorScheme.primary, style = MaterialTheme.typography.labelLarge)
                Text(service.name, style = MaterialTheme.typography.titleMedium)
                Text(service.description, maxLines = 2, overflow = TextOverflow.Ellipsis, color = MaterialTheme.colorScheme.onSurfaceVariant, style = MaterialTheme.typography.bodyMedium)
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text("From $${service.price.toInt()}", fontWeight = FontWeight.Bold)
                    Text("Book Now →", color = MaterialTheme.colorScheme.primary, style = MaterialTheme.typography.labelLarge)
                }
            }
        }
    }
}
