package com.y9san9.b0mb3r.controller


const val DEBUG = false

fun Bomber.handleLast(count: Int, successCount: Int, totalSuccess: Int, cycleNumber: Int){
    if(count == successCount) {
        push(BomberFinished(totalSuccess, StopReason.Success))
        return
    }
    push(CycleFinished(cycleNumber, successCount))
    if(successCount == 0){
        push(BomberFinished(totalSuccess, StopReason.LastLoopFailed))
        return
    }
    start(count - successCount, Bomber.CycleMeta(totalSuccess, cycleNumber))
}
