package org.grigoryfedorov.restaurantsmap.util.location

import java.io.IOException

class LocationException(
    message: String,
    cause: Throwable? = null
) : IOException(message, cause)