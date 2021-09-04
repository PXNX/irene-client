package nyx.felix.ui.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color.Companion.White

@Composable
fun FelixTheme(content: @Composable () -> Unit) {

    MaterialTheme(
        colors = darkColors(
            primary = White,
            primaryVariant = White,
            secondary = Teal200
        ),
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}