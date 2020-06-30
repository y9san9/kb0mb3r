package com.y9san9.b0mb3r.service

import com.github.kittinunf.fuel.core.DataPart
import com.github.kittinunf.fuel.core.FuelManager
import com.github.kittinunf.fuel.core.InlineDataPart
import com.github.kittinunf.fuel.core.Response
import com.y9san9.b0mb3r.phone.Phone
import com.y9san9.b0mb3r.utils.jsonify
import kotlin.concurrent.thread
import com.github.kittinunf.fuel.core.Method.POST
import com.github.kittinunf.fuel.core.extensions.authenticate
import com.github.kittinunf.fuel.core.extensions.authentication


enum class Method {
    GET, POST, FORM
}

class Service(
        private val request: Request,
        private val requestBuilder: Request.(phone: Phone) -> Unit
) {
    fun request(manager: FuelManager, phone: Phone, fn: Request.(Response?) -> Unit){
        request.apply {
            requestBuilder(phone)
            if(disable){
                fn(null)
            } else {
                thread {
                    val (_, response, _) = when (method) {
                        Method.POST -> manager.post(url, params.toList())
                        Method.GET -> manager.get(url, params.toList())
                        Method.FORM -> manager.upload(url, POST).apply {
                            if(params.isEmpty()) {
                                for ((name, value) in formData)
                                    add(InlineDataPart(value.first, name, contentType = value.second))
                            } else {
                                parameters = params.toList()
                            }
                        }
                    }.let {
                        it.body(body?.jsonify() ?: return@let it)
                    }.header(headers).let {
                        it.appendHeader("Content-Type" to (contentType ?: return@let it))
                    }.response()
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
        var params: MutableMap<String, Any?>,
        var formData: MutableMap<String, Pair<String, String>>,
        var body: Any?,
        var contentType: String?
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
    fun formData(vararg pairs: Pair<String, Pair<String, String>>) = formData.putAll(pairs)
    fun url(url: String) { this.url = url }
    fun body(body: Any?){ this.body = body }
    fun contentType(type: String){ contentType = type }
    fun json(vararg pairs: Pair<String, Any?>){ this.body = mapOf(*pairs) }
    fun disable(boolean: Boolean){ this.disable = boolean }
    fun enable(boolean: Boolean){ this.disable = !boolean }
}
