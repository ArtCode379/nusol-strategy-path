package nusol.management.nusolstrategypath.ui.composable.screen.splash

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.delay
import nusol.management.nusolstrategypath.R
import nusol.management.nusolstrategypath.ui.theme.StrategyBlue
import nusol.management.nusolstrategypath.ui.theme.StrategyBlueDark
import nusol.management.nusolstrategypath.ui.viewmodel.QJCXUSplashVM
import org.koin.androidx.compose.koinViewModel

@Composable
fun SplashScreen(
    modifier: Modifier = Modifier,
    viewModel: QJCXUSplashVM = koinViewModel(),
    onNavigateToHomeScreen: () -> Unit,
    onNavigateToOnboarding: () -> Unit,
) {
    val onboarded by viewModel.onboardedState.collectAsStateWithLifecycle()
    val progress = remember { Animatable(0f) }

    LaunchedEffect(Unit) {
        progress.animateTo(1f, tween(800))
        delay(700)
        if (onboarded) {
            onNavigateToHomeScreen()
        } else {
            onNavigateToOnboarding()
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(listOf(StrategyBlue, StrategyBlueDark))),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Image(
            painter = painterResource(R.drawable.icon),
            contentDescription = null,
            modifier = Modifier
                .size(104.dp)
                .scale(0.8f + progress.value * 0.2f)
                .alpha(progress.value)
                .clip(RoundedCornerShape(28.dp))
                .background(Color.White.copy(alpha = 0.12f)),
        )
        Text(
            text = "Nusol Strategy Path",
            color = Color.White,
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.alpha(progress.value),
        )
        Text(
            text = "Clarity for the next move",
            color = Color.White.copy(alpha = 0.74f),
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.alpha(progress.value),
        )
    }
}
