package org.grigoryfedorov.restaurantsmap.util.location

import android.content.Context
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.tasks.Task
import org.grigoryfedorov.restaurantsmap.domain.Location
import kotlin.coroutines.Continuation
import kotlin.coroutines.suspendCoroutine

class LocationManager(context: Context) {

    private val locationProviderClient: FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(context)

    suspend fun requestLocation() = suspendCoroutine<Location> { continuation ->
        locationProviderClient.lastLocation
            .addOnCompleteListener { task ->
                processCompletedTask(task, continuation)
            }.addOnCanceledListener {
                continuation.resumeWith(Result.failure(LocationException("Task canceled")))
            }.addOnFailureListener {
                continuation.resumeWith(Result.failure(it))
            }
    }

    private fun processCompletedTask(
        task: Task<android.location.Location>,
        continuation: Continuation<Location>
    ) {
        if (task.isSuccessful) {
            val taskResult = task.result
            if (taskResult == null) {
                continuation.resumeWith(
                    Result.failure(LocationException("Successful task with null result"))
                )
            } else {
                val resultLocation = Location(
                    lat = taskResult.latitude,
                    lon = taskResult.longitude
                )
                continuation.resumeWith(Result.success(resultLocation))
            }
        } else {
            continuation.resumeWith(Result.failure(LocationException("Unsuccessful task")))
        }
    }
}