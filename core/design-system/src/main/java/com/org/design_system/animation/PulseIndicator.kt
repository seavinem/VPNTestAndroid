package com.org.design_system.animation

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.withFrameNanos
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun PulseIndicator(
    @DrawableRes icon: Int,
    modifier: Modifier = Modifier,
    pulseSize: Dp = 60.dp,
    backgroundColor: Color = Color(0xFFC80000).copy(alpha = 0.2f),
    pulseColor: Color = Color(0xFFC80000).copy(alpha = 0.7f),
    iconColor: Color = Color(0xFFC80000)
) {
    val periodMs = 3600L
    val offsetsMs = longArrayOf(0L, 1200L, 2400L)

    val startNs = remember { System.nanoTime() }
    var frameTimeNs by remember { mutableLongStateOf(startNs) }

    LaunchedEffect(Unit) {
        while (true) {
            withFrameNanos { now -> frameTimeNs = now }
        }
    }

    fun phase(offsetMs: Long): Float {
        val elapsedMs = (frameTimeNs - startNs) / 1_000_000L + offsetMs
        return ((elapsedMs % periodMs).toFloat() / periodMs.toFloat())
    }

    Box(modifier.size(pulseSize), contentAlignment = Alignment.Center) {
        @Composable
        fun Ring(p: Float) = Box(
            Modifier
                .matchParentSize()
                .graphicsLayer {
                    scaleX = 1f + 0.8f * p
                    scaleY = 1f + 0.8f * p
                    alpha = 1f - p
                }
                .border(1.5.dp, pulseColor, CircleShape)
        )

        Ring(phase(offsetsMs[0]))
        Ring(phase(offsetsMs[1]))
        Ring(phase(offsetsMs[2]))

        Box(
            Modifier
                .size(pulseSize)
                .background(backgroundColor, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(icon),
                contentDescription = null,
                modifier = Modifier.size(32.dp),
                tint = iconColor
            )
        }
    }
}

