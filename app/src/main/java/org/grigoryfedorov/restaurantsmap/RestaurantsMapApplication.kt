package org.grigoryfedorov.restaurantsmap

import android.app.Application
import org.grigoryfedorov.restaurantsmap.di.MainModule
import org.grigoryfedorov.restaurantsmap.di.MainModuleProvider

class RestaurantsMapApplication : Application(), MainModuleProvider {

    override val mainModule: MainModule
        get() = _mainModule

    private lateinit var _mainModule: MainModule

    override fun onCreate() {
        super.onCreate()
        _mainModule = MainModule(this)
    }

}
