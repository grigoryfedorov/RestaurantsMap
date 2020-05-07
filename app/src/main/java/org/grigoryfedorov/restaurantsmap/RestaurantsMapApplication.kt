package org.grigoryfedorov.restaurantsmap

import android.app.Application
import org.grigoryfedorov.restaurantsmap.di.MainModule
import org.grigoryfedorov.restaurantsmap.di.MainModuleProvider

class RestaurantsMapApplication : Application(), MainModuleProvider {

    override lateinit var mainModule: MainModule

    override fun onCreate() {
        super.onCreate()
        mainModule = MainModule(context = this)
    }

}
