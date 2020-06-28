package com.y9san9.b0mb3r.utils

import kotlin.reflect.KProperty
import kotlin.reflect.KTypeParameter


class BaseDelegate<T>(private var value: T) {
    operator fun setValue(any: Any, param: KProperty<*>, value: T){
        this.value = value
    }
    operator fun getValue(any: Any, param: KProperty<*>) : T {
        return value
    }
}

fun <T> baseDelegate(value: T) : BaseDelegate<T> {
    return BaseDelegate(value)
}
