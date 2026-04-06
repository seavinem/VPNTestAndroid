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
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.org.design_system.theme.VpnColors
import com.org.design_system.theme.VpnDemoTheme
import com.org.design_system.theme.VpnTextStyle
import com.org.home.model.VpnState

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
        VpnState.ERROR -> VpnColors.Error.copy(alpha = 0.15f)
        else -> VpnColors.SurfaceVariant.copy(alpha = 0.6f)
    }
    val iconTint = when (vpnState) {
        VpnState.CONNECTED -> VpnColors.Tertiary
        VpnState.CONNECTING -> VpnColors.Primary
        VpnState.ERROR -> VpnColors.Error
        else -> VpnColors.Outline
    }
    val glowColor = when (vpnState) {
        VpnState.CONNECTED -> VpnColors.Tertiary
        VpnState.CONNECTING -> VpnColors.Primary
        VpnState.ERROR -> VpnColors.Error
        else -> VpnColors.Primary
    }

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(32.dp)
    ) {
        val scale = if (vpnState == VpnState.CONNECTING) pulseScale else 1f

        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(240.dp)
                .graphicsLayer {
                    scaleX = scale
                    scaleY = scale
                }
        ) {
            Box(
                modifier = Modifier
                    .size(288.dp)
                    .background(
                        Brush.radialGradient(
                            colors = listOf(glowColor.copy(alpha = 0.07f), Color.Transparent)
                        ),
                        CircleShape
                    )
            )

            Box(
                modifier = Modifier
                    .size(240.dp)
                    .background(Color.White.copy(alpha = 0.04f), CircleShape)
                    .border(1.dp, Color.White.copy(alpha = 0.05f), CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Box(
                    modifier = Modifier
                        .size(192.dp)
                        .clip(CircleShape)
                        .background(orbBackground)
                        .border(1.dp, VpnColors.Border, CircleShape)
                        .clickable(onClick = onOrbClick),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Filled.PowerSettingsNew,
                        contentDescription = when (vpnState) {
                            VpnState.CONNECTED, VpnState.CONNECTING -> "Disconnect"
                            else -> "Connect"
                        },
                        tint = iconTint,
                        modifier = Modifier.size(56.dp)
                    )
                }
            }
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = when (vpnState) {
                    VpnState.CONNECTED -> "Connected"
                    VpnState.CONNECTING -> "Connecting..."
                    VpnState.DISCONNECTING -> "Disconnecting..."
                    VpnState.ERROR -> "Connection Failed"
                    VpnState.DISCONNECTED -> "Not Connected"
                },
                style = VpnTextStyle.StatusTitle,
                color = VpnColors.OnSurface
            )
            Text(
                text = when (vpnState) {
                    VpnState.CONNECTED -> "YOUR CONNECTION IS SECURE"
                    VpnState.CONNECTING -> "ESTABLISHING SECURE TUNNEL"
                    VpnState.ERROR -> "TAP TO TRY AGAIN"
                    else -> "TAP TO SECURE YOUR CONNECTION"
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

@Preview(showBackground = true, backgroundColor = 0xFF0F1923)
@Composable
private fun ConnectionSectionErrorPreview() {
    VpnDemoTheme {
        ConnectionSection(vpnState = VpnState.ERROR, onOrbClick = {})
    }
}
