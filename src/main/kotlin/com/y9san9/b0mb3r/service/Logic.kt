package com.y9san9.b0mb3r.service

import com.github.kittinunf.fuel.core.FuelManager
import com.github.kittinunf.fuel.core.interceptors.LogRequestAsCurlInterceptor
import com.github.kittinunf.fuel.core.interceptors.LogResponseInterceptor
import com.y9san9.b0mb3r.controller.DEBUG
import com.y9san9.b0mb3r.phone.Phone


val manager = FuelManager().apply {
     @Suppress("ConstantConditionIf")
     if(DEBUG) {
          addRequestInterceptor(LogRequestAsCurlInterceptor)
          addResponseInterceptor(LogResponseInterceptor)
     }
}
val services = mutableListOf<Service>()

fun service(
     url: String = "",
     method: Method = Method.POST,
     params: MutableMap<String, Any> = mutableMapOf(),
     data: Any? = null,
     fn: Request.(Phone) -> Unit = {}){
     services.add(Service(url, method, params, data, fn))
}
