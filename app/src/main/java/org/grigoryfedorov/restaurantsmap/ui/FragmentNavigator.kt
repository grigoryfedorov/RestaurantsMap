package org.grigoryfedorov.restaurantsmap.ui

import android.os.Bundle
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

class FragmentNavigator(
    private val fragmentManager: FragmentManager,
    @IdRes private val containerId: Int
) {

    fun replaceFragment(
        fragment: Class<out Fragment>,
        addToBackStack: Boolean,
        arguments: Bundle? = null
    ) {
        val transaction = fragmentManager.beginTransaction()
            .replace(containerId, fragment, arguments)

        if (addToBackStack) {
            transaction.addToBackStack(null)
        }

        transaction.commit()
    }
}
