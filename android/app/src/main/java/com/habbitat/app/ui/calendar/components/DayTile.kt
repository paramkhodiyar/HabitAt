package com.habbitat.app.ui.calendar.components

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.habbitat.app.ui.calendar.DayTileData
import com.habbitat.app.ui.calendar.TileState
import com.habbitat.app.ui.theme.CardSurface
import com.habbitat.app.ui.theme.GlassBorder
import com.habbitat.app.ui.theme.HabbitAtTypography
import com.habbitat.app.ui.theme.InkMuted
import com.habbitat.app.ui.theme.InkPrimary
import com.habbitat.app.ui.theme.InkSecondary
import com.habbitat.app.ui.theme.SaffronPrimary
import com.habbitat.app.ui.theme.TerracottaTertiary

@Composable
fun DayTile(
    tileData: DayTileData,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    if (tileData.dayOfMonth == -1) {
        Box(modifier = modifier.aspectRatio(1f))
        return
    }

    val cornerShape = RoundedCornerShape(10.dp)

    when (tileData.state) {
        TileState.COMPLETED -> {
            Box(
                modifier = modifier
                    .aspectRatio(1f)
                    .clip(cornerShape)
                    .border(width = 1.dp, color = GlassBorder, shape = cornerShape)
                    .clickable(enabled = tileData.record != null, onClick = onClick)
            ) {
                if (tileData.record?.imageLocalUri != null) {
                    Image(
                        painter = rememberAsyncImagePainter(tileData.record.imageLocalUri),
                        contentDescription = "Proof photo for day ${tileData.dayOfMonth}",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                } else {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(SaffronPrimary.copy(alpha = 0.2f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "${tileData.dayOfMonth}",
                            style = HabbitAtTypography.labelLarge,
                            color = InkPrimary
                        )
                    }
                }

                // Saffron corner accent badge with vector check icon (DESIGN.md §8)
                Box(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .offset(x = (-2).dp, y = 2.dp)
                        .size(16.dp)
                        .clip(CircleShape)
                        .background(SaffronPrimary),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Check,
                        contentDescription = "Completed",
                        tint = CardSurface,
                        modifier = Modifier.size(10.dp)
                    )
                }
            }
        }

        TileState.MISSED -> {
            Box(
                modifier = modifier
                    .aspectRatio(1f)
                    .clip(cornerShape)
                    .background(Color(0x1F2A2320))
                    .border(
                        width = 1.dp,
                        color = TerracottaTertiary.copy(alpha = 0.35f), // Terracotta hairline (no shame-red)
                        shape = cornerShape
                    )
                    .clickable(onClick = onClick),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "${tileData.dayOfMonth}",
                    style = HabbitAtTypography.labelMedium,
                    fontSize = 11.sp,
                    color = InkMuted
                )
            }
        }

        TileState.TODAY_INCOMPLETE -> {
            // Pulsing saffron outline (DESIGN.md §8)
            val infiniteTransition = rememberInfiniteTransition(label = "pulse_tile")
            val pulseScale by infiniteTransition.animateFloat(
                initialValue = 0.98f,
                targetValue = 1.04f,
                animationSpec = infiniteRepeatable(
                    animation = tween(800, easing = FastOutSlowInEasing),
                    repeatMode = RepeatMode.Reverse
                ),
                label = "tile_scale"
            )

            Box(
                modifier = modifier
                    .aspectRatio(1f)
                    .scale(pulseScale)
                    .clip(cornerShape)
                    .background(CardSurface)
                    .border(width = 2.dp, color = SaffronPrimary, shape = cornerShape)
                    .clickable(onClick = onClick),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "${tileData.dayOfMonth}",
                    style = HabbitAtTypography.labelLarge,
                    color = SaffronPrimary
                )
            }
        }

        TileState.FUTURE -> {
            Box(
                modifier = modifier
                    .aspectRatio(1f)
                    .clip(cornerShape)
                    .border(width = 1.dp, color = GlassBorder, shape = cornerShape)
                    .clickable(onClick = onClick),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "${tileData.dayOfMonth}",
                    style = HabbitAtTypography.labelMedium,
                    fontSize = 11.sp,
                    color = InkSecondary.copy(alpha = 0.6f)
                )
            }
        }
    }
}
