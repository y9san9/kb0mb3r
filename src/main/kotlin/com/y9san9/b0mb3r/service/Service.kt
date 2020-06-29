package com.y9san9.b0mb3r.service

import com.github.kittinunf.fuel.core.Response
import com.y9san9.b0mb3r.phone.Phone
import com.y9san9.b0mb3r.utils.jsonify
import kotlin.concurrent.thread


enum class Method {
    GET, POST
}

class Service(
        private val url: String,
        private val method: Method,
        private val headers: MutableMap<String, String>,
        private val params: MutableMap<String, Any?>,
        private val body: Any?,
        private val requestBuilder: Request.(phone: Phone) -> Unit
) {
    fun request(phone: Phone, fn: Request.(Response?) -> Unit){
        Request(url, method, headers, params, body).apply {
            requestBuilder(phone)
            if(disable){
                fn(null)
            } else {
                thread {
                    val (_, response, _) = when (method) {
                        Method.POST -> manager.post(url, params.toList())
                        Method.GET -> manager.get(url, params.toList())
                    }.body(body.jsonify()).header(headers).response()
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
        var headers: MutableMap<String, String>,
        var params: MutableMap<String, Any?> = mutableMapOf(),
        var body: Any? = null
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

    fun headers(vararg pairs: Pair<String, String>) = headers.putAll(pairs)
    fun params(vararg pairs: Pair<String, Any?>) = params.putAll(pairs)
    fun url(url: String) { this.url = url }
    fun body(body: Any?){ this.body = body }
    fun json(vararg pairs: Pair<String, Any?>){ this.body = mapOf(*pairs) }
    fun disable(boolean: Boolean){ this.disable = boolean }
    fun enable(boolean: Boolean){ this.disable = !boolean }
}
