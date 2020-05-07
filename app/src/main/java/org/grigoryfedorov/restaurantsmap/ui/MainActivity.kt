package org.grigoryfedorov.restaurantsmap.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import org.grigoryfedorov.restaurantsmap.R
import org.grigoryfedorov.restaurantsmap.di.MainModuleProvider

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    override fun onCreate(savedInstanceState: Bundle?) {
        val mainModule = (applicationContext as MainModuleProvider).mainModule
        supportFragmentManager.fragmentFactory =
            MainFragmentFactory(mainModule)
        super.onCreate(savedInstanceState)

        val navigationViewModel = ViewModelProvider(this).get(NavigationViewModel::class.java)
        initNavigation(navigationViewModel)

        if (savedInstanceState == null) {
            navigationViewModel.map()
        }
    }

    private fun initNavigation(navigationViewModel: NavigationViewModel) {
        val fragmentNavigator =
            FragmentNavigator(
                supportFragmentManager,
                R.id.main_fragment_container
            )
        val mainNavigator =
            MainNavigator(fragmentNavigator)

        navigationViewModel.screen.observe(this, Observer {
            mainNavigator.navigate(it)
        })
    }
}