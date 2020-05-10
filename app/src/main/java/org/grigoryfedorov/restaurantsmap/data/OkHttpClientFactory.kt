package org.grigoryfedorov.restaurantsmap.data

import okhttp3.Interceptor
import okhttp3.OkHttpClient

class OkHttpClientFactory {

    fun create(vararg interceptors: Interceptor) : OkHttpClient {
        val builder = OkHttpClient.Builder()

        for (interceptor in interceptors) {
            builder.addInterceptor(interceptor)
        }

        return builder.build()
    }
}
