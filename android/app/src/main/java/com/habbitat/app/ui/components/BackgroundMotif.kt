package com.habitAt.app.ui.components

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import com.habitAt.app.ui.theme.LineArtMuted
import kotlin.math.cos
import kotlin.math.sin

enum class MotifVariant {
    KOLAM_DOT_GRID,    // Floating Kolam dot grid motif
    PLEY_MANGO_LEAF,   // Soft looping arch lines
    BORDER_GEOMETRY    // Subtle particle dots
}

@Composable
fun BackgroundMotif(
    variant: MotifVariant = MotifVariant.KOLAM_DOT_GRID,
    modifier: Modifier = Modifier,
    color: Color = LineArtMuted
) {
    val motion = rememberInfiniteTransition(label = "background_motif")
    val alphaPulse by motion.animateFloat(
        initialValue = 0.05f,
        targetValue = 0.09f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 4000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "alpha_pulse"
    )

    Canvas(modifier = modifier.fillMaxSize()) {
        val width = size.width
        val height = size.height

        val gridSpacing = 44.dp.toPx()
        val baseRadius = 1.5.dp.toPx()
        val strokeWidth = 0.8.dp.toPx()

        val drawColor = color.copy(alpha = alphaPulse)

        var y = gridSpacing / 2
        while (y < height) {
            var x = gridSpacing / 2
            while (x < width) {
                drawCircle(
                    color = drawColor,
                    radius = baseRadius,
                    center = Offset(x, y)
                )
                x += gridSpacing
            }
            y += gridSpacing
        }

        if (variant == MotifVariant.KOLAM_DOT_GRID) {
            // Static lightweight Kolam accent curves
            val path = Path().apply {
                var cy = gridSpacing * 3
                while (cy < height - gridSpacing * 2) {
                    var cx = gridSpacing * 3
                    while (cx < width - gridSpacing * 2) {
                        moveTo(cx - 20.dp.toPx(), cy)
                        cubicTo(
                            cx - 20.dp.toPx(), cy - 28.dp.toPx(),
                            cx + 20.dp.toPx(), cy - 28.dp.toPx(),
                            cx + 20.dp.toPx(), cy
                        )
                        cubicTo(
                            cx + 20.dp.toPx(), cy + 28.dp.toPx(),
                            cx - 20.dp.toPx(), cy + 28.dp.toPx(),
                            cx - 20.dp.toPx(), cy
                        )
                        cx += gridSpacing * 5
                    }
                    cy += gridSpacing * 6
                }
            }
            drawPath(path = path, color = drawColor, style = Stroke(width = strokeWidth))
        }
    }
}

