package com.habbitat.app.ui.calendar.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.CheckCircle
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.LocalFireDepartment
import androidx.compose.material.icons.rounded.Schedule
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import coil.compose.rememberAsyncImagePainter
import com.habbitat.app.data.local.entity.CompletionRecord
import com.habbitat.app.data.local.entity.Habit
import com.habbitat.app.ui.theme.CardSurface
import com.habbitat.app.ui.theme.GlassBorder
import com.habbitat.app.ui.theme.HabbitAtTypography
import com.habbitat.app.ui.theme.IndigoLight
import com.habbitat.app.ui.theme.IndigoSecondary
import com.habbitat.app.ui.theme.InkMuted
import com.habbitat.app.ui.theme.InkPrimary
import com.habbitat.app.ui.theme.InkSecondary
import com.habbitat.app.ui.theme.SaffronGlow
import com.habbitat.app.ui.theme.SaffronPrimary
import com.habbitat.app.ui.theme.TurmericGreenSuccess
import com.habbitat.app.ui.theme.TurmericLight
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun ProofDetailModal(
    habit: Habit?,
    record: CompletionRecord,
    onDismiss: () -> Unit
) {
    val completedDateFormatted = remember(record.completedAt) {
        val sdf = SimpleDateFormat("MMM d, yyyy 'at' h:mm a", Locale.getDefault())
        sdf.format(Date(record.completedAt))
    }

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth(0.92f)
                .border(width = 1.dp, color = GlassBorder, shape = RoundedCornerShape(28.dp)),
            shape = RoundedCornerShape(28.dp),
            colors = CardDefaults.cardColors(containerColor = CardSurface),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
        ) {
            Column(
                modifier = Modifier.padding(24.dp)
            ) {
                // Header Row
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = habit?.name ?: "Habit Proof",
                            style = HabbitAtTypography.displaySmall,
                            color = InkPrimary
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Rounded.Schedule,
                                contentDescription = null,
                                tint = InkMuted,
                                modifier = Modifier.size(14.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = completedDateFormatted,
                                style = HabbitAtTypography.bodyMedium,
                                color = InkSecondary
                            )
                        }
                    }

                    IconButton(onClick = onDismiss) {
                        Icon(
                            imageVector = Icons.Rounded.Close,
                            contentDescription = "Close",
                            tint = InkMuted
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Full Proof Image View
                if (record.imageLocalUri != null) {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(280.dp)
                            .border(width = 1.dp, color = GlassBorder, shape = RoundedCornerShape(20.dp)),
                        shape = RoundedCornerShape(20.dp)
                    ) {
                        Image(
                            painter = rememberAsyncImagePainter(record.imageLocalUri),
                            contentDescription = "Full proof image",
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                // AI Verification Badge & Details
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .clip(CircleShape)
                            .background(TurmericLight)
                            .padding(horizontal = 12.dp, vertical = 6.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Rounded.CheckCircle,
                                contentDescription = null,
                                tint = TurmericGreenSuccess,
                                modifier = Modifier.size(16.dp)
                            )
                            Text(
                                text = "Verified (${(record.confidence * 100).toInt()}%)",
                                style = HabbitAtTypography.labelMedium,
                                color = TurmericGreenSuccess
                            )
                        }
                    }

                    Box(
                        modifier = Modifier
                            .clip(CircleShape)
                            .background(SaffronGlow)
                            .padding(horizontal = 12.dp, vertical = 6.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Rounded.LocalFireDepartment,
                                contentDescription = null,
                                tint = SaffronPrimary,
                                modifier = Modifier.size(16.dp)
                            )
                            Text(
                                text = "Streak Day ${record.streakAtCompletion}",
                                style = HabbitAtTypography.labelMedium,
                                color = SaffronPrimary
                            )
                        }
                    }
                }
            }
        }
    }
}
