package org.grigoryfedorov.restaurantsmap.ui.navigation

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import org.grigoryfedorov.restaurantsmap.ui.SingleLiveEvent

class NavigationViewModel : ViewModel() {

    private val _screen =
        SingleLiveEvent<Screen>()

    val screen: LiveData<Screen>
        get() = _screen

    fun map() {
        setScreen(Screen.Map)
    }

    fun details(venueId: String) {
        setScreen(Screen.Details(venueId))
    }

    private fun setScreen(screen: Screen) {
        _screen.value = screen
    }
}
