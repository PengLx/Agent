package com.chsteam.agent.function

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import androidx.core.app.ActivityCompat
import com.chsteam.agent.AgentActivity
import com.chsteam.agent.api.ChatFunction
import com.google.android.gms.location.LocationServices
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale


class SystemFunction : Function() {

    companion object {
        const val ID = "SYSTEM"
    }

    override val functionList: List<ChatFunction>
        get() = listOf(
            ChatFunction("getCurrentTime", "Return the user local time", ChatFunction.Parameters()),
            ChatFunction("getCurrentLocation", "Return the user location of city", ChatFunction.Parameters())
        )

    override fun execute(name: String, vararg params: String) {
        val result = when(name) {
            "getCurrentTime" -> getCurrentTime()
            "getCurrentLocation" -> getCurrentLocation(context = AgentActivity.context)
            else -> {"NONE"}
        }

        pushResult(result)
    }

    private fun getCurrentTime(): String {
        val current = LocalDateTime.now()

        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")

        return current.format(formatter)
    }

    private fun getCurrentLocation(context: Context) : String {


        val loc = LocationServices.getFusedLocationProviderClient(context)


        return if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            "Permission Denied"
        } else {
            getCityFromLocation(loc.lastLocation.result, context = context) ?: "Failed"
        }
    }


    private fun getCityFromLocation(location: Location, context: Context): String? {
        val geocoder = Geocoder(context, Locale.getDefault())
        return try {
            val addresses = geocoder.getFromLocation(location.latitude, location.longitude, 1)
            addresses?.let {
                if(addresses.isNotEmpty()) {
                    addresses[0].locality
                } else null
            }
        } catch (e: Exception) {
            // Handle the exception
            null
        }
    }
}