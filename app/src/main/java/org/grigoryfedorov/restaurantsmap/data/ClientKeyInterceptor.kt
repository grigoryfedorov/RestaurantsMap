package org.grigoryfedorov.restaurantsmap.data

import org.grigoryfedorov.restaurantsmap.R
import org.grigoryfedorov.restaurantsmap.util.resource.ResourceManager


class ClientKeyInterceptor(resourceManager: ResourceManager) {
    private val clientIdKey = resourceManager.string(R.string.foursquare_client_id_key)
    private val clientSecretKey = resourceManager.string(R.string.foursquare_client_secret_key)


    val interceptor = QueryParamsInterceptor(listOf(
        QueryParamsInterceptor.QueryParameter(
            name = "client_id",
            value = clientIdKey
        ),
        QueryParamsInterceptor.QueryParameter(
            name = "client_secret",
            value = clientSecretKey
        )
    ))
}
