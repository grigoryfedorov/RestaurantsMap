package org.grigoryfedorov.restaurantsmap.util.permission

import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment

class PermissionRequester(
    private val fragment: Fragment,
    private val permissionMapper: PermissionMapper
) {

    fun requestPermissions(
        permissions: List<Permission>,
        resultCallback: () -> Unit
    ) {
        fragment.registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) {
            resultCallback()
        }.launch(permissionMapper.mapPermissions(permissions).toTypedArray())
    }
}
