package com.y9san9.b0mb3r.phone


class Phone {
    var code: Int?
    var body: Long?

    val text: String?
        get() {
            return "${code ?: return null}${body ?: return null}"
        }

    constructor(phone: String) {
        val (code, body) = phone.parsePhone() ?: null to null
        this.code = code
        this.body = body
    }

    constructor(code: Int?, body: Long?){
        this.code = code
        this.body = body
    }

    fun code(code: Int) : Phone {
        return Phone(code, body)
    }

    override fun toString(): String {
        return text ?: ""
    }
}
