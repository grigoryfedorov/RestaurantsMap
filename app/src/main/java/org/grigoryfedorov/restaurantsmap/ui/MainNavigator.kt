package org.grigoryfedorov.restaurantsmap.ui

class MainNavigator(private val fragmentNavigator: FragmentNavigator) {

    fun navigate(screen: Screen) {
        when (screen) {
            Screen.Map -> fragmentNavigator.replaceFragment(
                fragment = MapsFragment::class.java,
                addToBackStack = false
            )
        }
    }
}
