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

        delay(Random.nextLong(1_000L, 3_000L))

        if (Random.nextBoolean()) {
            delay(Random.nextLong(500L, 1_500L))
            emit(VpnState.CONNECTED)
        } else {
            emit(VpnState.ERROR)
        }
    }
}
