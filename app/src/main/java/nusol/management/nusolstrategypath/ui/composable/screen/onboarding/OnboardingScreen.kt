package nusol.management.nusolstrategypath.ui.composable.screen.onboarding

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import kotlinx.coroutines.launch
import nusol.management.nusolstrategypath.ui.viewmodel.QJCXUOnboardingVM
import org.koin.androidx.compose.koinViewModel

private data class OnboardingPage(val title: String, val description: String, val image: String)

private val pages = listOf(
    OnboardingPage(
        "See the whole system",
        "Explore focused consulting solutions for strategy, organization and operational performance.",
        "https://images.unsplash.com/photo-1552664730-d307ca884978?w=1200&q=85",
    ),
    OnboardingPage(
        "Make stronger decisions",
        "Work with experienced advisors to turn complex business questions into practical priorities.",
        "https://images.unsplash.com/photo-1454165804606-c3d57bc86b40?w=1200&q=85",
    ),
    OnboardingPage(
        "Move from plan to progress",
        "Book a consultation, agree the next steps and keep every strategic session within reach.",
        "https://images.unsplash.com/photo-1521737711867-e3b97375f902?w=1200&q=85",
    ),
)

@Composable
fun OnboardingScreen(
    modifier: Modifier = Modifier,
    viewModel: QJCXUOnboardingVM = koinViewModel(),
    onNavigateToHomeScreen: () -> Unit,
) {
    val completed by viewModel.onboardingSetState.collectAsState()
    val pagerState = rememberPagerState(pageCount = { pages.size })
    val scope = rememberCoroutineScope()

    LaunchedEffect(completed) {
        if (completed) {
            onNavigateToHomeScreen()
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.weight(1f),
        ) { pageIndex ->
            val page = pages[pageIndex]
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {
                AsyncImage(
                    model = page.image,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(250.dp)
                        .clip(RoundedCornerShape(24.dp)),
                )
                Spacer(Modifier.height(28.dp))
                Text(page.title, style = MaterialTheme.typography.headlineMedium)
                Spacer(Modifier.height(12.dp))
                Text(page.description, style = MaterialTheme.typography.bodyLarge, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
        }
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            pages.indices.forEach { index ->
                Spacer(
                    Modifier
                        .size(if (pagerState.currentPage == index) 24.dp else 8.dp, 8.dp)
                        .clip(CircleShape)
                        .background(if (pagerState.currentPage == index) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outline),
                )
            }
        }
        Spacer(Modifier.height(24.dp))
        Button(
            onClick = {
                if (pagerState.currentPage == pages.lastIndex) {
                    viewModel.setOnboarded()
                } else {
                    scope.launch { pagerState.animateScrollToPage(pagerState.currentPage + 1) }
                }
            },
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text(if (pagerState.currentPage == pages.lastIndex) "Get Started" else "Continue")
        }
    }
}
