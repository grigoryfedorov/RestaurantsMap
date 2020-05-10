package org.grigoryfedorov.restaurantsmap.data

import okhttp3.Interceptor
import okhttp3.Response

class QueryParamsInterceptor(private val params: List<QueryParameter>) : Interceptor {

    data class QueryParameter(
        val name: String,
        val value: String
    )

    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        val originalHttpUrl = original.url

        val urlBuilder = originalHttpUrl.newBuilder()

        for (param in params) {
            urlBuilder.addQueryParameter(param.name, param.value)
        }

        val request = original.newBuilder()
            .url(urlBuilder.build())
            .build()

        return chain.proceed(request)
    }

}
