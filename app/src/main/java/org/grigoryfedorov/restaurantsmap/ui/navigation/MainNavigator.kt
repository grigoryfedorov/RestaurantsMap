package org.grigoryfedorov.restaurantsmap.ui.navigation

import org.grigoryfedorov.restaurantsmap.ui.details.DetailsFragment
import org.grigoryfedorov.restaurantsmap.ui.map.MapFragment

class MainNavigator(private val fragmentNavigator: FragmentNavigator) {

    fun navigate(screen: Screen) {
        return when (screen) {
            Screen.Map -> fragmentNavigator.replaceFragment(
                fragment = MapFragment::class.java,
                addToBackStack = false
            )
            is Screen.Details -> fragmentNavigator.replaceFragment(
                fragment = DetailsFragment::class.java,
                addToBackStack = true,
                arguments = DetailsFragment.createArguments(screen.venueId)
            )
        }
    }
}
