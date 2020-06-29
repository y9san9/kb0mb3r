package com.y9san9.b0mb3r.service

import services.servicesInitializer


object ServiceFactory {
    fun build() = mutableListOf<Service>().apply(servicesInitializer)
}
