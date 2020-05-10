package org.grigoryfedorov.restaurantsmap.data.host

import org.grigoryfedorov.restaurantsmap.util.resource.ResourceManager
import org.grigoryfedorov.restaurantsmap.R

class FoursquareApiHostProvider(private val resourceManager: ResourceManager) :
    HostProvider {
    override val host: String
        get() = resourceManager.string(R.string.foursquare_api_host)
}
