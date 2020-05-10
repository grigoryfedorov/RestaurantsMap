package org.grigoryfedorov.restaurantsmap.data

import org.grigoryfedorov.restaurantsmap.R
import org.grigoryfedorov.restaurantsmap.util.resource.ResourceManager


class ApiVersionInterceptor(resourceManager: ResourceManager) {
    private val apiVersion = resourceManager.string(R.string.foursquare_api_version)

    val interceptor = QueryParamsInterceptor(listOf(
        QueryParamsInterceptor.QueryParameter(
            name = "v",
            value = apiVersion
        )
    ))
}
