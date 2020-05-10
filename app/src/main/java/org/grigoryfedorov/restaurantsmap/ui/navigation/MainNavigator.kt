package org.grigoryfedorov.restaurantsmap.ui.navigation

import org.grigoryfedorov.restaurantsmap.ui.map.MapFragment

class MainNavigator(private val fragmentNavigator: FragmentNavigator) {

    fun navigate(screen: Screen) {
        when (screen) {
            Screen.Map -> fragmentNavigator.replaceFragment(
                fragment = MapFragment::class.java,
                addToBackStack = false
            )
        }
    }
}
