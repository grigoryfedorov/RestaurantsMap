package org.grigoryfedorov.restaurantsmap.util.resource

import android.content.Context

class ResourceManagerImpl(private val context: Context) :
    ResourceManager {
    override fun string(resourceId: Int): String {
        return context.resources.getString(resourceId)
    }
}
