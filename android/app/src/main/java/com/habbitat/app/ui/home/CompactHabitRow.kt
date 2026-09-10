package com.habitAt.app.ui.home

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.CameraAlt
import androidx.compose.material.icons.rounded.CheckCircle
import androidx.compose.material.icons.rounded.LocalFireDepartment
import androidx.compose.material.icons.rounded.Schedule
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.habitAt.app.data.DueStatusInfo
import com.habitAt.app.data.local.entity.Habit
import com.habitAt.app.ui.theme.CardSurface
import com.habitAt.app.ui.theme.GlassBorder
import com.habitAt.app.ui.theme.habitAtMotion
import com.habitAt.app.ui.theme.habitAtTypography
import com.habitAt.app.ui.theme.IndigoLight
import com.habitAt.app.ui.theme.IndigoSecondary
import com.habitAt.app.ui.theme.InkMuted
import com.habitAt.app.ui.theme.InkPrimary
import com.habitAt.app.ui.theme.InkSecondary
import com.habitAt.app.ui.theme.SaffronGlow
import com.habitAt.app.ui.theme.SaffronPrimary
import com.habitAt.app.ui.theme.TurmericGreenSuccess
import com.habitAt.app.ui.theme.TurmericLight

@Composable
fun CompactHabitRow(
    habit: Habit,
    streakCount: Int,
    dueStatus: DueStatusInfo,
    onOpenDetails: () -> Unit,
    onProofClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.98f else 1.0f,
        animationSpec = habitAtMotion.SpringBouncy,
        label = "row_press_scale"
    )

    Card(
        modifier = modifier
            .fillMaxWidth()
            .scale(scale)
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = onOpenDetails
            )
            .border(width = 1.dp, color = GlassBorder, shape = RoundedCornerShape(16.dp)),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = CardSurface),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = habit.name,
                        style = habitAtTypography.headlineMedium,
                        fontSize = 17.sp,
                        color = InkPrimary
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(6.dp))
                            .background(IndigoLight)
                            .padding(horizontal = 6.dp, vertical = 2.dp)
                    ) {
                        Text(
                            text = habit.frequency,
                            style = habitAtTypography.labelSmall,
                            color = IndigoSecondary,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                Spacer(modifier = Modifier.height(4.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Rounded.Schedule,
                        contentDescription = null,
                        tint = InkMuted,
                        modifier = Modifier.size(13.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "${habit.targetDurationMinutes} min target",
                        style = habitAtTypography.bodyMedium,
                        fontSize = 13.sp,
                        color = InkSecondary
                    )

                    Spacer(modifier = Modifier.width(10.dp))

                    // Due Status Label
                    Text(
                        text = dueStatus.statusText,
                        style = habitAtTypography.labelSmall,
                        color = if (dueStatus.isCompletedPeriod) TurmericGreenSuccess else InkSecondary
                    )
                }
            }

            Spacer(modifier = Modifier.width(10.dp))

            // Streak Flame Badge
            Box(
                modifier = Modifier
                    .clip(CircleShape)
                    .background(SaffronGlow)
                    .padding(horizontal = 10.dp, vertical = 5.dp),
                contentAlignment = Alignment.Center
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(3.dp)
                ) {
                    Icon(
                        imageVector = Icons.Rounded.LocalFireDepartment,
                        contentDescription = "Streak",
                        tint = SaffronPrimary,
                        modifier = Modifier.size(16.dp)
                    )
                    Text(
                        text = "$streakCount",
                        style = habitAtTypography.displaySmall,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = SaffronPrimary
                    )
                }
            }

            Spacer(modifier = Modifier.width(10.dp))

            // CTA Button / Badge
            if (dueStatus.isCompletedPeriod) {
                Box(
                    modifier = Modifier
                        .clip(CircleShape)
                        .background(TurmericLight)
                        .padding(8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Rounded.CheckCircle,
                        contentDescription = "Completed",
                        tint = TurmericGreenSuccess,
                        modifier = Modifier.size(20.dp)
                    )
                }
            } else {
                Box(
                    modifier = Modifier
                        .clip(CircleShape)
                        .background(SaffronPrimary)
                        .clickable(onClick = onProofClick)
                        .padding(8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Rounded.CameraAlt,
                        contentDescription = "Log Proof",
                        tint = InkPrimary,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
        }
    }
}
