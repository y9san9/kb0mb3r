package com.y9san9.b0mb3r.service

import com.github.kittinunf.fuel.core.Response
import com.y9san9.b0mb3r.phone.Phone
import com.y9san9.b0mb3r.utils.getRandomUserAgent
import com.y9san9.b0mb3r.utils.jsonify
import kotlin.concurrent.thread


enum class Method {
    GET, POST
}

class Service(
    private val url: String,
    private val method: Method,
    private val params: MutableMap<String, Any>,
    private val data: Any?,
    private val requestBuilder: Request.(phone: Phone) -> Unit
) {
    private val headers = mapOf(
        "User-Agent" to getRandomUserAgent(),
        "X-Requested-With" to "XMLHttpRequest"
    )
    fun request(phone: String, fn: Request.(Response?) -> Unit){
        Request(url, method, params, data).apply {
            requestBuilder(Phone(phone))
            if(disable){
                fn(null)
            } else {
                thread {
                    val (_, response, _) = when (method) {
                        Method.POST -> manager.post(url, params.toList())
                        Method.GET -> manager.get(url, params.toList())
                    }.body(data.jsonify()).header(headers).response()
                    fn(response)
                }
            }
        }
    }
}

typealias ResponseValidator = (Response?) -> Boolean

class Request(
    var url: String,
    var method: Method = Method.POST,
    var params: MutableMap<String, Any> = mutableMapOf(),
    var data: Any? = null
) {
    var disable = false

    var validator: ResponseValidator = { it?.statusCode == 200 }
        private set

    /**
     * @note Response may be null if service disabled or error while loading
     **/
    fun validate(validator: ResponseValidator){
        this.validator = validator
    }

    fun params(vararg pairs: Pair<String, Any>) = params.putAll(pairs)
    fun url(url: String) { this.url = url }
    fun data(data: Any?){ this.data = data }
    fun disable(boolean: Boolean){ this.disable = boolean }
    fun enable(boolean: Boolean){ this.disable = !boolean }
}
