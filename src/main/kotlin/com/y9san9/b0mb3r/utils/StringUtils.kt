package com.y9san9.b0mb3r.utils

import java.lang.StringBuilder


fun shuffleString(string: String = "ABCDEFGHIJKklmnopqrst4567890_") : String {
    return string.shuffle()
}

fun String.shuffle() : String {
    return asIterable().shuffled().joinToString()
}

fun String.replace(regex: Regex) = replace(regex, "")
fun String.replaceFirst(string: String) = replaceFirst(string, "")

fun randomEmail() : String {
    return "${"abcdefgacsonokadc".shuffle()}@gmail.com"
}
