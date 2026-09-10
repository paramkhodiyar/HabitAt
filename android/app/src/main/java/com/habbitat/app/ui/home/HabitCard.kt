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
import androidx.compose.ui.unit.sp
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
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
import com.habitAt.app.ui.theme.SaffronLight
import com.habitAt.app.ui.theme.SaffronPrimary

import androidx.compose.material.icons.rounded.CheckCircle
import com.habitAt.app.ui.theme.TurmericGreenSuccess
import com.habitAt.app.ui.theme.TurmericLight

@Composable
fun HabitCard(
    habit: Habit,
    streakCount: Int = 0,
    isCompletedToday: Boolean = false,
    onOpenDetails: () -> Unit = {},
    onProofClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.98f else 1.0f,
        animationSpec = habitAtMotion.SpringBouncy,
        label = "card_press_scale"
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
            .border(width = 1.dp, color = GlassBorder, shape = RoundedCornerShape(20.dp)),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = CardSurface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(20.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Habit Name, Duration & Frequency Chip
                Column(modifier = Modifier.weight(1f)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = habit.name,
                            style = habitAtTypography.headlineMedium,
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
                            contentDescription = "Target Duration",
                            tint = InkMuted,
                            modifier = Modifier.size(14.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "${habit.targetDurationMinutes} min target",
                            style = habitAtTypography.bodyMedium,
                            color = InkSecondary
                        )
                    }
                }

                // Streak Badge with flame glow
                Box(
                    modifier = Modifier
                        .clip(CircleShape)
                        .background(SaffronGlow)
                        .padding(horizontal = 12.dp, vertical = 6.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.LocalFireDepartment,
                            contentDescription = "Streak Flame",
                            tint = SaffronPrimary,
                            modifier = Modifier.size(18.dp)
                        )
                        Text(
                            text = "$streakCount",
                            style = habitAtTypography.displaySmall,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = SaffronPrimary
                        )
                    }
                }
            }

            if (habit.proofDescription.isNotBlank()) {
                Spacer(modifier = Modifier.height(12.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp))
                        .background(IndigoLight)
                        .padding(horizontal = 12.dp, vertical = 8.dp)
                ) {
                    Text(
                        text = "Proof: ${habit.proofDescription}",
                        style = habitAtTypography.bodyMedium,
                        color = IndigoSecondary
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            if (isCompletedToday) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(14.dp))
                        .background(TurmericLight)
                        .padding(vertical = 12.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Rounded.CheckCircle,
                            contentDescription = "Verified Today",
                            tint = TurmericGreenSuccess,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Verified & Completed Today",
                            style = habitAtTypography.labelLarge,
                            color = TurmericGreenSuccess,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            } else {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(14.dp))
                        .background(SaffronPrimary)
                        .clickable(
                            interactionSource = interactionSource,
                            indication = null,
                            onClick = onProofClick
                        )
                        .padding(vertical = 12.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Rounded.CameraAlt,
                        contentDescription = "Submit Proof",
                        tint = InkPrimary,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Log Photo Proof",
                        style = habitAtTypography.labelLarge,
                        color = InkPrimary
                    )
                }
            }
        }
    }
}
