package org.grigoryfedorov.restaurantsmap.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel

class NavigationViewModel : ViewModel() {

    private val _screen =
        SingleLiveEvent<Screen>()

    val screen: LiveData<Screen>
        get() = _screen

    fun map() {
        setScreen(Screen.Map)
    }

    private fun setScreen(screen: Screen) {
        _screen.value = screen
    }
}
