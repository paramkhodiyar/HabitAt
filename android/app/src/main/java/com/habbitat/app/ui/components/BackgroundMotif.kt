package com.habbitat.app.ui.components

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
import com.habbitat.app.ui.theme.LineArtMuted
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
    val phase by motion.animateFloat(
        initialValue = 0f,
        targetValue = (2 * Math.PI).toFloat(),
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 12000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "particle_phase"
    )

    Canvas(modifier = modifier.fillMaxSize()) {
        val width = size.width
        val height = size.height

        val gridSpacing = 44.dp.toPx()
        val baseRadius = 1.6.dp.toPx()
        val strokeWidth = 0.8.dp.toPx()

        var y = gridSpacing / 2
        var colIndex = 0
        while (y < height) {
            var x = gridSpacing / 2
            var rowIndex = 0
            while (x < width) {
                // Compute organic floating displacement using double sine/cosine wave
                val dx = sin(phase + colIndex * 0.5f + rowIndex * 0.3f) * 4.dp.toPx()
                val dy = cos(phase * 0.8f + colIndex * 0.3f + rowIndex * 0.6f) * 4.dp.toPx()
                val pulseRadius = baseRadius + sin(phase + colIndex + rowIndex) * 0.4.dp.toPx()

                drawCircle(
                    color = color.copy(alpha = 0.08f),
                    radius = pulseRadius.coerceAtLeast(1.dp.toPx()),
                    center = Offset(x + dx, y + dy)
                )

                x += gridSpacing
                rowIndex++
            }
            y += gridSpacing
            colIndex++
        }

        if (variant == MotifVariant.KOLAM_DOT_GRID) {
            // Soft Kolam accent curves in corners
            val path = Path().apply {
                var cy = gridSpacing * 3
                while (cy < height - gridSpacing * 2) {
                    var cx = gridSpacing * 3
                    while (cx < width - gridSpacing * 2) {
                        val floatOffset = sin(phase + cx * 0.01f) * 3.dp.toPx()
                        moveTo(cx - 20.dp.toPx(), cy + floatOffset)
                        cubicTo(
                            cx - 20.dp.toPx(), cy - 28.dp.toPx() + floatOffset,
                            cx + 20.dp.toPx(), cy - 28.dp.toPx() + floatOffset,
                            cx + 20.dp.toPx(), cy + floatOffset
                        )
                        cubicTo(
                            cx + 20.dp.toPx(), cy + 28.dp.toPx() + floatOffset,
                            cx - 20.dp.toPx(), cy + 28.dp.toPx() + floatOffset,
                            cx - 20.dp.toPx(), cy + floatOffset
                        )
                        cx += gridSpacing * 5
                    }
                    cy += gridSpacing * 6
                }
            }
            drawPath(path = path, color = color.copy(alpha = 0.05f), style = Stroke(width = strokeWidth))
        }
    }
}

