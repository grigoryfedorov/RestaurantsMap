package org.grigoryfedorov.restaurantsmap.ui.navigation

sealed class Screen {
    object Map: Screen()
    data class Details(val venueId: String): Screen()
}
