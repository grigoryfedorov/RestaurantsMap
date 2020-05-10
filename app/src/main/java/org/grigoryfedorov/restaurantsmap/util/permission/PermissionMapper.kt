package org.grigoryfedorov.restaurantsmap.util.permission

import android.Manifest

class PermissionMapper {

    fun mapPermission(permission: Permission): String {
        return when (permission) {
            Permission.LOCATION -> Manifest.permission.ACCESS_FINE_LOCATION
        }
    }

    fun mapPermissions(permissions: List<Permission>): List<String> {
        return permissions.map {
            mapPermission(it)
        }
    }
}