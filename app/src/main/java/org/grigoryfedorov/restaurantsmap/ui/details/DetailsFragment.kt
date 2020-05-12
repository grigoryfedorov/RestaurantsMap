package org.grigoryfedorov.restaurantsmap.ui.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import org.grigoryfedorov.restaurantsmap.databinding.DetailsFragmentBinding
import org.grigoryfedorov.restaurantsmap.di.MainModule

class DetailsFragment(private val mainModule: MainModule)
    : Fragment() {

    companion object {
        private const val ARG_VENUE_ID = "arg_venue_id"

        fun createArguments(venueId: String): Bundle {
            return Bundle().apply {
                putString(ARG_VENUE_ID, venueId)
            }
        }

        fun getVenueId(bundle: Bundle?): String? {
            return bundle?.getString(ARG_VENUE_ID)
        }

    }

    private lateinit var viewModel: DetailsViewModel

    private var _binding: DetailsFragmentBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val venueId = getVenueId(arguments)
            ?: throw IllegalArgumentException("No venue Id passed")

        viewModel = getViewModel(venueId)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DetailsFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewModelObservers()
    }

    override fun onStart() {
        super.onStart()
        viewModel.onStart()
    }

    override fun onStop() {
        viewModel.onStop()
        super.onStop()
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    private fun getViewModel(venueId: String): DetailsViewModel {
        return ViewModelProvider(
            this,
            DetailsViewModelFactory(mainModule, venueId)
        )
            .get(DetailsViewModel::class.java)
    }

    private fun initViewModelObservers() {
        viewModel.details.observe(viewLifecycleOwner, Observer {
            binding.apply {
                detailsName.text = it.venue.name
                detailsCategory.text = it.venue.category ?: ""
                detailsOpenStatus.text = it.hoursStatus ?: ""
                detailsRating.text = if (it.rating != null) "${it.rating}" else ""
            }
        })
    }




}
