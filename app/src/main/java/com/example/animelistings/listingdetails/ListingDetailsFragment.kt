package com.example.animelistings.listingdetails

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.animelistings.databinding.FragmentListingDetailsBinding
import com.example.animelistings.databinding.FragmentListingsBinding

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class ListingDetailsFragment : Fragment() {

    private var _binding: FragmentListingDetailsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private lateinit var viewModel: ListingDetailsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentListingDetailsBinding.inflate(inflater, container, false)

        val id = ListingDetailsFragmentArgs.fromBundle(requireArguments()).id
        val viewModelFactory = ListingDetailsViewModel.Factory(id, requireActivity().application)
        viewModel =
            ViewModelProvider(this, viewModelFactory)[ListingDetailsViewModel::class.java]
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        return binding.root

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}