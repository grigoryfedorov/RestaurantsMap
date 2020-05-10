package org.grigoryfedorov.restaurantsmap.util.resource

import androidx.annotation.StringRes

interface ResourceManager {
    fun string(@StringRes resourceId: Int): String
}
