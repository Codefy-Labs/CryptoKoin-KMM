package com.codefylabs.www.canimmigrate.android.ui.theme


import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext

// Define your light color palette
private val LightColorPalette = ColorScheme(
    primary = Color(0xFFD52B1E),
    onPrimary = Color(0xFFFFFFFF),
    primaryContainer = Color(0xFFFFCDD2),
    onPrimaryContainer = Color(0xFF49000A),
    inversePrimary = Color(0xFFFF9A9E),
    secondary = Color(0xFFFF7043),
    onSecondary = Color(0xFFFFFFFF),
    secondaryContainer = Color(0xFFFFCCBC),
    onSecondaryContainer = Color(0xFF5D0015),
    tertiary = Color(0xFFFFCC80),
    onTertiary = Color(0xFF000000),
    tertiaryContainer = Color(0xFFFFE0B2),
    onTertiaryContainer = Color(0xFF4F2B06),
    background = Color(0xFFF5F5F5),
    onBackground = Color(0xFF000000),
    surface = Color(0xFFFFFFFF),
    onSurface = Color(0xFF000000),
    surfaceVariant = Color(0xFFE0E0E0),
    onSurfaceVariant = Color(0xFF000000),
    surfaceTint = Color(0xFFD52B1E),
    inverseSurface = Color(0xFF303030),
    inverseOnSurface = Color(0xFFFFFFFF),
    error = Color(0xFFB00020),
    onError = Color(0xFFFFFFFF),
    errorContainer = Color(0xFFFFDAD4),
    onErrorContainer = Color(0xFF370007),
    outline = Color(0xFF757575),
    outlineVariant = Color(0xFFBDBDBD),
    scrim = Color(0xFF000000),
)

// Define your dark color palette
private val DarkColorPalette = ColorScheme(
    primary = Color(0xFFD52B1E),
    onPrimary = Color(0xFFFFFFFF),
    primaryContainer = Color(0xFF49000A),
    onPrimaryContainer = Color(0xFFFFCDD2),
    inversePrimary = Color(0xFFD52B1E),
    secondary = Color(0xFFFF8A50),
    onSecondary = Color(0xFF5D0015),
    secondaryContainer = Color(0xFF7D2C00),
    onSecondaryContainer = Color(0xFFFFCCBC),
    tertiary = Color(0xFFFFB74D),
    onTertiary = Color(0xFF4F2B06),
    tertiaryContainer = Color(0xFF8C4100),
    onTertiaryContainer = Color(0xFFFFE0B2),
    background = Color(0xFF121212),
    onBackground = Color(0xFFFFFFFF),
    surface = Color(0xFF212121),
    onSurface = Color(0xFFFFFFFF),
    surfaceVariant = Color(0xFF37474F),
    onSurfaceVariant = Color(0xFFE0E0E0),
    surfaceTint = Color(0xFFD52B1E),
    inverseSurface = Color(0xFFE0E0E0),
    inverseOnSurface = Color(0xFF000000),
    error = Color(0xFFFFB4A9),
    onError = Color(0xFF370007),
    errorContainer = Color(0xFF930006),
    onErrorContainer = Color(0xFFFFDAD4),
    outline = Color(0xFF8C8C8C),
    outlineVariant = Color(0xFFBDBDBD),
    scrim = Color(0xFF000000),
)

@Composable
fun AppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    val colors = if (darkTheme) DarkColorPalette else LightColorPalette
    MaterialTheme(
        colorScheme = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )

}


@Composable
fun KmmAppBackground(
    content: @Composable BoxScope.() -> Unit,
) {
    val statusBarLight = Color.Transparent
    val statusBarDark = Color.Transparent
    val navigationBarLight = Color.Transparent
    val navigationBarDark =Color.Transparent
    val isDarkMode = isSystemInDarkTheme()
    val context = LocalContext.current as ComponentActivity

    DisposableEffect(isDarkMode) {
        context.enableEdgeToEdge(
            statusBarStyle = if (!isDarkMode) {
                SystemBarStyle.light(
                    statusBarLight.toArgb(),
                    statusBarDark.toArgb()
                )
            } else {
                SystemBarStyle.dark(
                    statusBarDark.toArgb()
                )
            },
            navigationBarStyle = if(!isDarkMode){
                SystemBarStyle.light(
                    navigationBarLight.toArgb(),
                    navigationBarDark.toArgb()
                )
            } else {
                SystemBarStyle.dark(navigationBarDark.toArgb())
            }
        )
        onDispose { }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background)
    ) {
        content.invoke(this)
    }
}