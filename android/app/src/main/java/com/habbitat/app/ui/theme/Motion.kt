package com.habitAt.app.ui.theme

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring

object habitAtMotion {
    // Spring motion specs for physical UI transitions (DESIGN.md §7)
    val SpringBouncy = spring<Float>(
        dampingRatio = Spring.DampingRatioMediumBouncy,
        stiffness = Spring.StiffnessLow
    )

    val SpringSmooth = spring<Float>(
        dampingRatio = Spring.DampingRatioNoBouncy,
        stiffness = Spring.StiffnessMediumLow
    )

    val SpringStiff = spring<Float>(
        dampingRatio = Spring.DampingRatioNoBouncy,
        stiffness = Spring.StiffnessMedium
    )
}
