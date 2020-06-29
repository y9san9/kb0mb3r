package com.y9san9.b0mb3r

import com.y9san9.b0mb3r.controller.*
import com.y9san9.b0mb3r.service.ServiceFactory
import java.util.*

val scanner = Scanner(System.`in`)
fun main(){
    print("Phone number: ")
    Bomber(scanner.next()) {
        subscribe {
            when(it){
                is SmsSent -> println("Sms ${if (it.isSuccess) "successful" else "failed to"} sent via ${it.serviceName}")
                is CycleFinished -> println("Cycle ${it.cycleNumber} finished. Sent ${it.successCount} sms")
                is BomberFinished -> {
                    when(it.stopReason){
                        StopReason.Success -> {}
                        StopReason.LastLoopFailed ->
                            println("Last loop havent sent at least one sms, stopping bomber. " +
                                    "That's because some services banned or timeouted you by phone / ip")
                    }
                    println("Total sent: ${it.totalSent}")
                }
            }
        }
        print("SMS count: ")
        start(scanner.nextInt())
    }
}