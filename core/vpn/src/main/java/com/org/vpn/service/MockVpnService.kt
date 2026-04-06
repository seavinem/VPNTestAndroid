package com.org.vpn.service

import com.org.vpn.model.VpnState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import kotlin.random.Random

class MockVpnService @Inject constructor() {

    fun startConnection(country: String): Flow<VpnState> = flow {
        emit(VpnState.CONNECTING)
        delay(2_000L)

        if (country.isBlank() || !Random.nextBoolean()) {
            throw IllegalStateException("Connection failed")
        }

        emit(VpnState.CONNECTED)
    }
}
