package com.y9san9.b0mb3r.phone

import com.y9san9.b0mb3r.utils.replace
import com.y9san9.b0mb3r.utils.replaceFirst


val numberLengthToCode = mapOf(
    12 to listOf(380, 375),
    11 to listOf(7, 8)
)

fun String.parsePhone() : Pair<Int, Long>? {
    val phone = replace(Regex("\\D"))
    println(phone.length)
    println(phone)
    return numberLengthToCode[phone.length]?.firstOrNull { phone.startsWith(it.toString()) }?.let {
        it to phone.replaceFirst(it.toString()).toLong()
    }
}
