package com.y9san9.b0mb3r.utils

import java.lang.UnsupportedOperationException


fun shuffleString(string: String = "ABCDEFGHIJKklmnopqrst4567890_") : String {
    return string.shuffle()
}

fun String.shuffle() : String {
    return asIterable().shuffled().joinToString()
}

fun String.replace(regex: Regex) = replace(regex, "")
fun String.replace(string: String) = replace(string, "")
fun String.replaceFirst(string: String) = replaceFirst(string, "")


/**
 * @return null if mask symbols count not equals string length
 */
fun String.mask(mask: String, maskSymbol: Char = '#') : String? {
    val maskSymbolsLen = mask.length - mask.replace(maskSymbol.toString()).length
    if(length != maskSymbolsLen)
        return null
    var charIndex = -1
    return buildString {
        for(symbol in mask){
            append(if(symbol == maskSymbol){
                charIndex++
                this@mask[charIndex]
            } else {
                symbol
            })
        }
    }
}

fun randomEmail() : String {
    return "${"abcdefgacsonokadc".shuffle()}@gmail.com"
}
fun randomRussian() : String {
    return "ФбвгохзйыАолыдсиц".shuffle()
}
