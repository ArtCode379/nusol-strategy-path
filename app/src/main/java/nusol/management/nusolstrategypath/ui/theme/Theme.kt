package nusol.management.nusolstrategypath.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val AppColorScheme = lightColorScheme(
    primary = StrategyBlue,
    onPrimary = SurfaceWhite,
    primaryContainer = ChipBackground,
    onPrimaryContainer = StrategyBlueDark,
    secondary = InsightAmber,
    onSecondary = Ink,
    background = Canvas,
    onBackground = Ink,
    surface = SurfaceWhite,
    onSurface = Ink,
    surfaceVariant = ChipBackground,
    onSurfaceVariant = MutedInk,
    outline = Border,
    error = Warning,
)

@Composable
fun ServiceSkeletonTheme(
    darkTheme: Boolean = false,
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit,
) {
    MaterialTheme(
        colorScheme = AppColorScheme,
        typography = AppTypography,
        content = content,
    )
}
