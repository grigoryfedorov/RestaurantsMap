package org.grigoryfedorov.restaurantsmap.ui.map

import org.grigoryfedorov.restaurantsmap.domain.Location

data class CameraAction(
    val location: Location,
    val zoom: Float
)
