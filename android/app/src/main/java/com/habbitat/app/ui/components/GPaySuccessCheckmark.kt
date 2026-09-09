package com.habbitat.app.ui.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathMeasure
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.unit.dp
import com.habbitat.app.ui.theme.TurmericGreenSuccess

@Composable
fun GPaySuccessCheckmark(
    modifier: Modifier = Modifier,
    circleColor: Color = TurmericGreenSuccess,
    checkmarkColor: Color = Color.White,
    sizeDp: Int = 84
) {
    val haptic = LocalHapticFeedback.current

    val scaleAnim = remember { Animatable(0f) }
    val checkProgress = remember { Animatable(0f) }
    val rippleScale = remember { Animatable(0.8f) }
    val rippleAlpha = remember { Animatable(0.8f) }

    LaunchedEffect(Unit) {
        // Step 1: Spring scale entry of badge container
        scaleAnim.animateTo(
            targetValue = 1f,
            animationSpec = spring(
                dampingRatio = Spring.DampingRatioMediumBouncy,
                stiffness = Spring.StiffnessLow
            )
        )
        haptic.performHapticFeedback(HapticFeedbackType.LongPress)

        // Step 2: Draw checkmark path smoothly
        checkProgress.animateTo(
            targetValue = 1f,
            animationSpec = tween(durationMillis = 400, easing = FastOutSlowInEasing)
        )

        // Step 3: Radiate outer ripple ring
        rippleScale.animateTo(
            targetValue = 1.4f,
            animationSpec = tween(durationMillis = 500, easing = FastOutSlowInEasing)
        )
    }

    LaunchedEffect(Unit) {
        rippleAlpha.animateTo(
            targetValue = 0f,
            animationSpec = tween(durationMillis = 500, delayMillis = 200, easing = FastOutSlowInEasing)
        )
    }

    Box(
        modifier = modifier.size((sizeDp * 1.5).dp),
        contentAlignment = Alignment.Center
    ) {
        // Outer GPay-style expanding ripple ring
        Box(
            modifier = Modifier
                .size(sizeDp.dp)
                .scale(rippleScale.value)
                .alpha(rippleAlpha.value)
                .clip(CircleShape)
                .background(circleColor.copy(alpha = 0.35f))
        )

        // Main circular success badge
        Box(
            modifier = Modifier
                .size(sizeDp.dp)
                .scale(scaleAnim.value)
                .clip(CircleShape)
                .background(circleColor),
            contentAlignment = Alignment.Center
        ) {
            Canvas(modifier = Modifier.size((sizeDp * 0.5).dp)) {
                val fullPath = Path().apply {
                    val w = size.width
                    val h = size.height
                    // Checkmark vector path (start left-middle -> down -> top right)
                    moveTo(w * 0.18f, h * 0.52f)
                    lineTo(w * 0.42f, h * 0.78f)
                    lineTo(w * 0.85f, h * 0.24f)
                }

                val pathMeasure = PathMeasure()
                pathMeasure.setPath(fullPath, false)

                val segmentPath = Path()
                pathMeasure.getSegment(
                    startDistance = 0f,
                    stopDistance = pathMeasure.length * checkProgress.value,
                    destination = segmentPath,
                    startWithMoveTo = true
                )

                drawPath(
                    path = segmentPath,
                    color = checkmarkColor,
                    style = Stroke(
                        width = size.width * 0.12f,
                        cap = StrokeCap.Round,
                        join = StrokeJoin.Round
                    )
                )
            }
        }
    }
}
