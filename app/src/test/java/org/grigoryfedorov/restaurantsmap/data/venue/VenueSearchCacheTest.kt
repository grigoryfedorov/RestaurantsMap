package org.grigoryfedorov.restaurantsmap.data.venue

import io.mockk.every
import io.mockk.mockk
import org.grigoryfedorov.restaurantsmap.data.venue.model.LocationKey
import org.grigoryfedorov.restaurantsmap.domain.Location
import org.grigoryfedorov.restaurantsmap.domain.LocationBox
import org.grigoryfedorov.restaurantsmap.domain.Venue
import org.grigoryfedorov.restaurantsmap.domain.VenueLocation
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class VenueSearchCacheTest {

    private val locationKeyMapper: LocationKeyMapper = mockk(relaxed = true)

    lateinit var venueSearchCache: VenueSearchCache

    @Before
    fun setUp() {
        venueSearchCache = VenueSearchCache(locationKeyMapper)
    }

    @After
    fun tearDown() {
    }

    @Test
    fun shouldReturnEmptySearchIfNothingWasSaved() {
        checkSingleVenue(
            boxNeLat = 20.12345,
            boxNeLon = 50.53252,
            boxSwLat = 10.12415,
            boxSwLon = 40.45235,
            venueLat = 15.2623,
            venueLon = 45.62623,
            expectedToFind = false,
            saveVenue = false
        )
    }

    @Test
    fun shouldReturnVenueIfInTheMiddleOfTheBox() {
        checkSingleVenue(
            boxNeLat = 20.12345,
            boxNeLon = 50.53252,
            boxSwLat = 10.12415,
            boxSwLon = 40.45235,
            venueLat = 15.2623,
            venueLon = 45.62623,
            saveVenue = true,
            expectedToFind = true
        )
    }

    @Test
    fun shouldReturnVenueIfInTheCornerOfTheBox() {
        checkSingleVenue(
            boxNeLat = 20.12345,
            boxNeLon = 50.53252,
            boxSwLat = 10.3,
            boxSwLon = 40.4,
            venueLat = 10.3,
            venueLon = 40.4,
            saveVenue = true,
            expectedToFind = true
        )
    }

    @Test
    fun shouldReturnEmptyIfNearTheCornerButOutSideOfTheBox() {
        checkSingleVenue(
            boxNeLat = 20.1,
            boxNeLon = 50.2,
            boxSwLat = 10.12415,
            boxSwLon = 40.45235,
            venueLat = 20.2,
            venueLon = 50.2,
            saveVenue = true,
            expectedToFind = false
        )
    }

    @Test
    fun shouldReturnEmptyIfOutOfTheBoxByOneCoordinate() {
        checkSingleVenue(
            boxNeLat = 20.16346,
            boxNeLon = 50.234634,
            boxSwLat = 10.12415,
            boxSwLon = 40.45235,
            venueLat = 15.532523,
            venueLon = 60.52352,
            saveVenue = true,
            expectedToFind = false
        )
    }

    private fun checkSingleVenue(
        boxNeLat: Double,
        boxNeLon: Double,
        boxSwLat: Double,
        boxSwLon: Double,
        venueLat: Double,
        venueLon: Double,
        saveVenue: Boolean,
        expectedToFind: Boolean
    ) {
        val locationBox = LocationBox(
            northEast = Location(
                lat = boxNeLat,
                lon = boxNeLon
            ),
            southWest = Location(
                lat = boxSwLat,
                lon = boxSwLon
            )
        )

        val venue = Venue(
            id = "someId",
            name = "TestName",
            venueLocation = VenueLocation(
                Location(
                    lat = venueLat,
                    lon = venueLon
                ),
                address = null
            ),
            category = null
        )

        every {
            locationKeyMapper.map(any())
        } answers { LocationKey(
            lat = arg<Location>(0).lat.toInt(),
            lon = arg<Location>(0).lon.toInt())
        }

        if (saveVenue) {
            venueSearchCache.putVenues(listOf(venue))
        }

        val result = venueSearchCache.search(locationBox)

        Assert.assertEquals(expectedToFind, result.isNotEmpty())
        if (expectedToFind && result.isNotEmpty()) {
            Assert.assertEquals(venue, result[0])
        }
    }

}