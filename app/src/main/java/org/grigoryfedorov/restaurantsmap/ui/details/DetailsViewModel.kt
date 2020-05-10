package org.grigoryfedorov.restaurantsmap.ui.details

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.grigoryfedorov.restaurantsmap.domain.VenueDetails
import org.grigoryfedorov.restaurantsmap.interactor.DetailsInteractor

class DetailsViewModel(
    private val venueId: String,
    private val detailsInteractor: DetailsInteractor
) : ViewModel() {

    companion object {
        const val TAG = "DetailsViewModel"
    }

    private var getDetailsJob: Job? = null

    val details: LiveData<VenueDetails>
        get() = _details

    private val _details = MutableLiveData<VenueDetails>()

    fun onStart() {
        getDetailsJob = viewModelScope.launch {
            runCatching {
                detailsInteractor.getDetails(venueId)
            }.onSuccess {
                _details.value = it
            }.onFailure {
                Log.w(TAG, "Error getting details for venue $venueId ${it.message}", it)
            }
        }

    }

    fun onStop() {
        getDetailsJob?.cancel()
    }

}
