package com.y9san9.b0mb3r.service


val servicesInitializer = {
    /**
     *  Service example
     *  Checked 28.06.20. Only 70000000000 format available
     *  Define arguments as constant or inside lambda if it requires number
    **/
    service(method = Method.POST, data = hashMapOf("sender" to "Tele2")) { phone ->
        // Define url
        url("https://msk.tele2.ru/api/validation/number/${phone.code(7)}")

        // Define params
        params(
            "first" to "value",
            "second" to true
        )

        // Define body
        data(hashMapOf("sender" to "Tele2"))

        // Disable service if number incorrect
        disable(!listOf(7, 8).contains(phone.code))

        // Check if request success
        validate {
            it?.statusCode == 200
        }
    }
}
