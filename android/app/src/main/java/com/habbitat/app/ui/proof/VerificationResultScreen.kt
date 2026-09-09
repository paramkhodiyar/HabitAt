package com.habbitat.app.ui.proof

import android.net.Uri
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.scaleIn
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
import androidx.compose.material.icons.rounded.Psychology
import androidx.compose.material.icons.rounded.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.habbitat.app.data.local.entity.Habit
import com.habbitat.app.ui.components.BackgroundMotif
import com.habbitat.app.ui.components.MotifVariant
import com.habbitat.app.ui.theme.CardSurface
import com.habbitat.app.ui.theme.GlassBorder
import com.habbitat.app.ui.theme.HabbitAtTypography
import com.habbitat.app.ui.theme.IndigoLight
import com.habbitat.app.ui.theme.IndigoSecondary
import com.habbitat.app.ui.theme.InkPrimary
import com.habbitat.app.ui.theme.InkSecondary
import com.habbitat.app.ui.theme.SaffronLight
import com.habbitat.app.ui.theme.SaffronPrimary
import com.habbitat.app.ui.theme.TerracottaLight
import com.habbitat.app.ui.theme.TerracottaTertiary
import com.habbitat.app.ui.theme.TurmericGreenSuccess
import com.habbitat.app.ui.theme.TurmericLight
import com.habbitat.app.ui.theme.WarmIvory
import com.habbitat.app.verification.ProofVerifier
import com.habbitat.app.verification.VerificationResult
import com.habbitat.app.verification.VisionApiProofVerifier

@Composable
fun VerificationResultScreen(
    habit: Habit,
    imageUri: Uri,
    onAccept: (VerificationResult) -> Unit,
    onRetake: () -> Unit,
    verifier: ProofVerifier = remember { VisionApiProofVerifier() }
) {
    val context = LocalContext.current
    val haptic = LocalHapticFeedback.current

    var isVerifying by remember { mutableStateOf(true) }
    var result: VerificationResult? by remember { mutableStateOf(null) }

    LaunchedEffect(imageUri) {
        isVerifying = true
        val res = verifier.verify(habit, imageUri, context)
        result = res
        isVerifying = false

        // Trigger physical haptic feedback beat per DESIGN.md §7
        if (res.verified) {
            haptic.performHapticFeedback(HapticFeedbackType.LongPress)
        } else {
            haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(WarmIvory)
    ) {
        BackgroundMotif(variant = MotifVariant.KOLAM_DOT_GRID)

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Proof Thumbnail Preview
            Card(
                modifier = Modifier
                    .size(240.dp)
                    .border(width = 2.dp, color = GlassBorder, shape = RoundedCornerShape(24.dp)),
                shape = RoundedCornerShape(24.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
            ) {
                Image(
                    painter = rememberAsyncImagePainter(imageUri),
                    contentDescription = "Captured Proof",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            }

            Spacer(modifier = Modifier.height(28.dp))

            if (isVerifying) {
                // VERIFYING state (suspense beat with pulsing motion per DESIGN.md §7)
                val infiniteTransition = rememberInfiniteTransition(label = "pulse")
                val pulseScale by infiniteTransition.animateFloat(
                    initialValue = 0.95f,
                    targetValue = 1.05f,
                    animationSpec = infiniteRepeatable(
                        animation = tween(600, easing = FastOutSlowInEasing),
                        repeatMode = RepeatMode.Reverse
                    ),
                    label = "pulse_scale"
                )

                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Box(
                        modifier = Modifier
                            .scale(pulseScale)
                            .size(56.dp)
                            .clip(CircleShape)
                            .background(SaffronLight),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.Psychology,
                            contentDescription = null,
                            tint = SaffronPrimary,
                            modifier = Modifier.size(32.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = "VERIFYING PROOF…",
                        style = HabbitAtTypography.headlineMedium,
                        color = InkPrimary
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = "Analyzing photo against habit criteria",
                        style = HabbitAtTypography.bodyMedium,
                        color = InkSecondary
                    )
                }
            } else {
                val res = result
                if (res != null) {
                    AnimatedVisibility(
                        visible = true,
                        enter = fadeIn() + scaleIn()
                    ) {
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .border(width = 1.dp, color = GlassBorder, shape = RoundedCornerShape(24.dp)),
                            shape = RoundedCornerShape(24.dp),
                            colors = CardDefaults.cardColors(containerColor = CardSurface)
                        ) {
                            Column(
                                modifier = Modifier.padding(24.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                // Result Icon / GPay Animated Checkmark
                                if (res.verified) {
                                    com.habbitat.app.ui.components.GPaySuccessCheckmark(sizeDp = 76)
                                } else {
                                    Box(
                                        modifier = Modifier
                                            .size(64.dp)
                                            .clip(CircleShape)
                                            .background(TerracottaLight),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Icon(
                                            imageVector = Icons.Rounded.Close,
                                            contentDescription = null,
                                            tint = TerracottaTertiary,
                                            modifier = Modifier.size(36.dp)
                                        )
                                    }
                                }

                                Spacer(modifier = Modifier.height(16.dp))

                                Text(
                                    text = if (res.verified) "Proof Verified!" else "Verification Rejected",
                                    style = HabbitAtTypography.displaySmall,
                                    color = InkPrimary
                                )

                                Spacer(modifier = Modifier.height(8.dp))

                                Text(
                                    text = res.reason,
                                    style = HabbitAtTypography.bodyMedium,
                                    color = InkSecondary
                                )

                                Spacer(modifier = Modifier.height(12.dp))

                                // Confidence score pill
                                Box(
                                    modifier = Modifier
                                        .clip(CircleShape)
                                        .background(IndigoLight)
                                        .padding(horizontal = 12.dp, vertical = 6.dp)
                                ) {
                                    Text(
                                        text = "AI Confidence: ${(res.confidence * 100).toInt()}%",
                                        style = HabbitAtTypography.labelMedium,
                                        color = IndigoSecondary
                                    )
                                }

                                Spacer(modifier = Modifier.height(24.dp))

                                if (res.verified) {
                                    Button(
                                        onClick = { onAccept(res) },
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .height(48.dp),
                                        shape = RoundedCornerShape(14.dp),
                                        colors = ButtonDefaults.buttonColors(
                                            containerColor = SaffronPrimary,
                                            contentColor = InkPrimary
                                        )
                                    ) {
                                        Text(
                                            text = "Done & Update Streak",
                                            style = HabbitAtTypography.titleMedium,
                                            color = InkPrimary
                                        )
                                    }
                                } else {
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                                    ) {
                                        OutlinedButton(
                                            onClick = onRetake,
                                            modifier = Modifier
                                                .weight(1.1f)
                                                .height(48.dp),
                                            shape = RoundedCornerShape(14.dp),
                                            contentPadding = androidx.compose.foundation.layout.PaddingValues(horizontal = 6.dp)
                                        ) {
                                            Icon(
                                                imageVector = Icons.Rounded.Refresh,
                                                contentDescription = null,
                                                modifier = Modifier.size(16.dp)
                                            )
                                            Spacer(modifier = Modifier.width(4.dp))
                                            Text(
                                                text = "Retake Photo",
                                                maxLines = 1,
                                                softWrap = false,
                                                style = HabbitAtTypography.labelMedium
                                            )
                                        }

                                        Button(
                                            onClick = { onAccept(res) },
                                            modifier = Modifier
                                                .weight(1f)
                                                .height(48.dp),
                                            shape = RoundedCornerShape(14.dp),
                                            colors = ButtonDefaults.buttonColors(
                                                containerColor = TerracottaTertiary,
                                                contentColor = CardSurface
                                            )
                                        ) {
                                            Text("Dismiss")
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
