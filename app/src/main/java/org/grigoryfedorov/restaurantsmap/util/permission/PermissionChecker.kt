package org.grigoryfedorov.restaurantsmap.util.permission

import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat

class PermissionChecker(
    private val context: Context,
    private val permissionMapper: PermissionMapper
) {

    fun hasPermission(permission: Permission): Boolean {
        val permissionName = permissionMapper.mapPermission(permission)

        val permissionCheckResult = ContextCompat.checkSelfPermission(
            context,
            permissionName
        )
        return permissionCheckResult == PackageManager.PERMISSION_GRANTED
    }

}