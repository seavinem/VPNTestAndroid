package com.org.home.compose

import androidx.compose.animation.core.EaseInOut
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PowerSettingsNew
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.org.design_system.theme.VpnColors
import com.org.design_system.theme.VpnDemoTheme
import com.org.design_system.theme.VpnTextStyle
import com.org.features.home.R
import com.org.vpn.model.VpnState

@Composable
fun ConnectionSection(
    vpnState: VpnState,
    onOrbClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val infiniteTransition = rememberInfiniteTransition(label = "vpn_pulse")
    val pulseScale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.06f,
        animationSpec = infiniteRepeatable(
            animation = tween(900, easing = EaseInOut),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulse_scale"
    )

    val orbBackground = when (vpnState) {
        VpnState.CONNECTED -> VpnColors.Tertiary.copy(alpha = 0.15f)
        VpnState.CONNECTING -> VpnColors.Primary.copy(alpha = 0.12f)
        VpnState.DISCONNECTED -> VpnColors.SurfaceVariant.copy(alpha = 0.6f)
    }
    val iconTint = when (vpnState) {
        VpnState.CONNECTED -> VpnColors.Tertiary
        VpnState.CONNECTING -> VpnColors.Primary
        VpnState.DISCONNECTED -> VpnColors.Outline
    }
    val glowColor = when (vpnState) {
        VpnState.CONNECTED -> VpnColors.Tertiary
        VpnState.CONNECTING -> VpnColors.Primary
        VpnState.DISCONNECTED -> VpnColors.Primary
    }

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(32.dp)
    ) {
        val scale = if (vpnState == VpnState.CONNECTING) pulseScale else 1f

        BoxWithConstraints(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            val outerSize = (maxWidth * 0.62f)
                .coerceAtLeast(180.dp)
                .coerceAtMost(280.dp)
                .coerceAtMost(maxWidth)
            val glowSize = (outerSize * 1.2f).coerceAtMost(maxWidth)
            val orbSize = (outerSize * 0.8f)
                .coerceAtLeast(148.dp)
                .coerceAtMost(216.dp)
            val iconSize = (orbSize * 0.29f)
                .coerceAtLeast(44.dp)
                .coerceAtMost(56.dp)

            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(outerSize)
                    .graphicsLayer {
                        scaleX = scale
                        scaleY = scale
                    }
            ) {
                Box(
                    modifier = Modifier
                        .size(glowSize)
                        .background(
                            Brush.radialGradient(
                                colors = listOf(glowColor.copy(alpha = 0.07f), Color.Transparent)
                            ),
                            CircleShape
                        )
                )

                Box(
                    modifier = Modifier
                        .size(outerSize)
                        .background(Color.White.copy(alpha = 0.04f), CircleShape)
                        .border(1.dp, Color.White.copy(alpha = 0.05f), CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Box(
                        modifier = Modifier
                            .size(orbSize)
                            .clip(CircleShape)
                            .background(orbBackground)
                            .border(1.dp, VpnColors.Border, CircleShape)
                            .clickable(onClick = onOrbClick),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Filled.PowerSettingsNew,
                            contentDescription = when (vpnState) {
                                VpnState.CONNECTED, VpnState.CONNECTING -> stringResource(R.string.home_disconnect)
                                else -> stringResource(R.string.home_connect)
                            },
                            tint = iconTint,
                            modifier = Modifier.size(iconSize)
                        )
                    }
                }
            }
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = when (vpnState) {
                    VpnState.CONNECTED -> stringResource(R.string.home_status_connected)
                    VpnState.CONNECTING -> stringResource(R.string.home_status_connecting)
                    VpnState.DISCONNECTED -> stringResource(R.string.home_status_disconnected)
                },
                style = VpnTextStyle.StatusTitle,
                color = VpnColors.OnSurface
            )
            Text(
                text = when (vpnState) {
                    VpnState.CONNECTED -> stringResource(R.string.home_status_connected_subtitle)
                    VpnState.CONNECTING -> stringResource(R.string.home_status_connecting_subtitle)
                    VpnState.DISCONNECTED -> stringResource(R.string.home_status_disconnected_subtitle)
                },
                style = VpnTextStyle.StatusSubtitle,
                color = VpnColors.Outline,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF0F1923)
@Composable
private fun ConnectionSectionDisconnectedPreview() {
    VpnDemoTheme {
        ConnectionSection(vpnState = VpnState.DISCONNECTED, onOrbClick = {})
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF0F1923)
@Composable
private fun ConnectionSectionConnectingPreview() {
    VpnDemoTheme {
        ConnectionSection(vpnState = VpnState.CONNECTING, onOrbClick = {})
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF0F1923)
@Composable
private fun ConnectionSectionConnectedPreview() {
    VpnDemoTheme {
        ConnectionSection(vpnState = VpnState.CONNECTED, onOrbClick = {})
    }
}
