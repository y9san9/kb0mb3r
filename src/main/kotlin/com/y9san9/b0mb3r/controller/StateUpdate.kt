package com.y9san9.b0mb3r.controller


enum class StopReason {
    Success, LastLoopFailed, ErrorResolvingNumber
}

sealed class StateUpdate

class SmsSent(val isSuccess: Boolean, val serviceName: String) : StateUpdate()
class BomberFinished(val totalSent: Int, val stopReason: StopReason) : StateUpdate()
class CycleFinished(val cycleNumber: Int, val successCount: Int) : StateUpdate()
