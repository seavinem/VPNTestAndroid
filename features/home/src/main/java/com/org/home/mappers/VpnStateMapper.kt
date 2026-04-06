package com.org.home.mappers

import com.org.home.model.VpnState
import com.org.vpn.model.VpnState as VpnServiceState

internal fun VpnServiceState.toDomain(): VpnState = when (this) {
    VpnServiceState.CONNECTED -> VpnState.CONNECTED
    VpnServiceState.CONNECTING -> VpnState.CONNECTING
    VpnServiceState.DISCONNECTED -> VpnState.DISCONNECTED
    VpnServiceState.DISCONNECTING -> VpnState.DISCONNECTING
    VpnServiceState.ERROR -> VpnState.ERROR
}