package com.y9san9.b0mb3r

import com.y9san9.b0mb3r.controller.*
import com.y9san9.b0mb3r.phone.numberLengthToCode
import com.y9san9.b0mb3r.service.ServiceFactory
import java.net.InetSocketAddress
import java.net.Proxy
import java.net.SocketAddress
import java.util.*

val scanner = Scanner(System.`in`)
fun main(){
    print("Phone number: ")
    Bomber(scanner.nextLine()) {
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
                        StopReason.ErrorResolvingNumber ->
                            println("Your number is invalid or not supported at the moment,\n" +
                                    "You can add it to com.y9san9.b0mb3r.phone.numberLengthToCode and send PR or " +
                                    "write me in Telegram to me make it\n" +
                                    "Available codes: " +
                                    numberLengthToCode.values.joinToString { codes -> codes.joinToString() })
                    }
                    println("Total sent: ${it.totalSent}")
                }
            }
        }
        print("SMS count: ")
        start(scanner.nextInt())
    }
}