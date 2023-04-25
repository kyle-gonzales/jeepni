package com.example.jeepni.util

interface PermissionTextProvider {
    fun getDescription(isPermanentlyDeclined : Boolean) : String
}

class LocationPermissionTextProvider : PermissionTextProvider {
    override fun getDescription(isPermanentlyDeclined: Boolean): String {
        return if (isPermanentlyDeclined) {
            "It seems you permanently declined location permissions.  Go to the app settings to grant it."
        } else {
            "JeepNi needs access to your location to give you a better app experience."
        }
    }
}