package org.grigoryfedorov.restaurantsmap.ui.main

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import org.grigoryfedorov.restaurantsmap.di.MainModule
import org.grigoryfedorov.restaurantsmap.ui.map.MapFragment

class MainFragmentFactory(
    private val mainModule: MainModule
) : FragmentFactory() {
    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
        return when(className) {
            MapFragment::class.java.name -> MapFragment(
                mainModule
            )
            else -> super.instantiate(classLoader, className)
        }
    }
}
