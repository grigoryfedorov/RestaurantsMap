package org.grigoryfedorov.restaurantsmap.ui

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import org.grigoryfedorov.restaurantsmap.di.MainModule

class MainFragmentFactory(
    private val mainModule: MainModule
) : FragmentFactory() {
    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
        return when(className) {
            MapsFragment::class.java.name -> MapsFragment()
            else -> super.instantiate(classLoader, className)
        }
    }
}
