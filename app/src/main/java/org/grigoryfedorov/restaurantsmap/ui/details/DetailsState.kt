package org.grigoryfedorov.restaurantsmap.ui.details

sealed class DetailsState {
    object Progress: DetailsState()

    data class Content(
        val name: String,
        val category: String?,
        val rating: String?,
        val openStatus: String?,
        val image: String?
    ) : DetailsState()

    data class Error(
        val message: String
    ) : DetailsState()
}