package com.habbitat.app.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.BatteryAlert
import androidx.compose.material.icons.rounded.NotificationsActive
import androidx.compose.material.icons.rounded.PhotoCamera
import androidx.compose.material.icons.rounded.Schedule
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.habbitat.app.ui.theme.CardSurface
import com.habbitat.app.ui.theme.GlassBorder
import com.habbitat.app.ui.theme.HabbitAtTypography
import com.habbitat.app.ui.theme.IndigoLight
import com.habbitat.app.ui.theme.IndigoSecondary
import com.habbitat.app.ui.theme.InkMuted
import com.habbitat.app.ui.theme.InkPrimary
import com.habbitat.app.ui.theme.InkSecondary
import com.habbitat.app.ui.theme.SaffronLight
import com.habbitat.app.ui.theme.SaffronPrimary

@Composable
fun PermissionDialog(
    onDismiss: () -> Unit,
    onRequestPermissions: () -> Unit
) {
    Dialog(onDismissRequest = onDismiss) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .border(width = 1.dp, color = GlassBorder, shape = RoundedCornerShape(24.dp)),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = CardSurface)
        ) {
            Column(
                modifier = Modifier.padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .size(52.dp)
                        .clip(CircleShape)
                        .background(SaffronLight),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Rounded.NotificationsActive,
                        contentDescription = null,
                        tint = SaffronPrimary,
                        modifier = Modifier.size(26.dp)
                    )
                }

                Spacer(modifier = Modifier.height(14.dp))

                Text(
                    text = "Habit Enforcement Setup",
                    style = HabbitAtTypography.headlineMedium,
                    color = InkPrimary
                )

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = "HabitAt needs exact scheduling and notifications to ensure your escalating reminders survive Doze and Standby modes.",
                    style = HabbitAtTypography.bodyMedium,
                    color = InkSecondary
                )

                Spacer(modifier = Modifier.height(16.dp))

                PermissionFeatureItem(
                    icon = Icons.Rounded.NotificationsActive,
                    title = "Post Notifications",
                    subtitle = "Delivers escalating reminder prompts when habits are due"
                )

                Spacer(modifier = Modifier.height(10.dp))

                PermissionFeatureItem(
                    icon = Icons.Rounded.PhotoCamera,
                    title = "Camera Access for Proof",
                    subtitle = "Captures daily photo evidence to verify habit completions"
                )

                Spacer(modifier = Modifier.height(10.dp))

                PermissionFeatureItem(
                    icon = Icons.Rounded.Schedule,
                    title = "Exact Alarm Timing",
                    subtitle = "Fires your initial reminder precisely at your chosen time"
                )

                Spacer(modifier = Modifier.height(10.dp))

                PermissionFeatureItem(
                    icon = Icons.Rounded.BatteryAlert,
                    title = "Battery Optimization Exclusion",
                    subtitle = "Ensures background escalations run reliably during Doze mode"
                )

                Spacer(modifier = Modifier.height(20.dp))

                Button(
                    onClick = onRequestPermissions,
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(min = 52.dp),
                    shape = RoundedCornerShape(14.dp),
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = SaffronPrimary,
                        contentColor = InkPrimary
                    )
                ) {
                    Text(
                        text = "Grant Enforcement Permissions",
                        style = HabbitAtTypography.labelLarge,
                        color = InkPrimary,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }

                Spacer(modifier = Modifier.height(6.dp))

                TextButton(onClick = onDismiss) {
                    Text(
                        text = "Maybe Later",
                        style = HabbitAtTypography.labelLarge,
                        color = InkMuted
                    )
                }
            }
        }
    }
}

@Composable
private fun PermissionFeatureItem(
    icon: ImageVector,
    title: String,
    subtitle: String
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(34.dp)
                .clip(CircleShape)
                .background(IndigoLight),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = IndigoSecondary,
                modifier = Modifier.size(16.dp)
            )
        }

        Spacer(modifier = Modifier.width(10.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = title,
                style = HabbitAtTypography.titleMedium,
                color = InkPrimary
            )
            Text(
                text = subtitle,
                style = HabbitAtTypography.bodyMedium,
                color = InkSecondary
            )
        }
    }
}
