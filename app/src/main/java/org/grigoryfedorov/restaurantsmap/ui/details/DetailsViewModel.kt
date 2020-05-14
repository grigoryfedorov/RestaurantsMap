package org.grigoryfedorov.restaurantsmap.ui.details

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.grigoryfedorov.restaurantsmap.R
import org.grigoryfedorov.restaurantsmap.interactor.DetailsInteractor
import org.grigoryfedorov.restaurantsmap.util.resource.ResourceManager

class DetailsViewModel(
    private val venueId: String,
    private val detailsInteractor: DetailsInteractor,
    private val resourceManager: ResourceManager
) : ViewModel() {

    companion object {
        const val TAG = "DetailsViewModel"
    }

    private var getDetailsJob: Job? = null

    val state: LiveData<DetailsState>
        get() = _state

    private val _state = MutableLiveData<DetailsState>()

    fun onStart() {
        _state.value = DetailsState.Progress
        getDetailsJob = viewModelScope.launch {
            runCatching {
                detailsInteractor.getDetails(venueId).collect {
                    Log.i(TAG, "Got details for $venueId $it")
                    _state.value = DetailsState.Content(it)
                }
            }.onFailure {
                Log.w(TAG, "Error getting details for venue $venueId ${it.message}", it)
                _state.value = DetailsState.Error(resourceManager.string(R.string.venue_details_error_))
            }
        }

    }

    fun onStop() {
        getDetailsJob?.cancel()
    }

}
