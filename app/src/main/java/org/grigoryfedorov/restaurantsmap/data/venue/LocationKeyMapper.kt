package org.grigoryfedorov.restaurantsmap.data.venue

import org.grigoryfedorov.restaurantsmap.data.venue.model.LocationKey
import org.grigoryfedorov.restaurantsmap.domain.Location
import kotlin.math.pow
import kotlin.math.roundToInt

class LocationKeyMapper {
    companion object {
        // number of decimal places after dot
        // the more the number, the smaller would be location boxes stored
        const val DOUBLE_DECIMAL_PLACES = 3
    }

    fun map(location: Location): LocationKey {
        return LocationKey(
            lat = roundDoubleToInt(location.lat),
            lon = roundDoubleToInt(location.lon)
        )
    }

    private fun roundDoubleToInt(double: Double): Int {
        return (double * 10.0.pow(DOUBLE_DECIMAL_PLACES)).roundToInt()
    }
}