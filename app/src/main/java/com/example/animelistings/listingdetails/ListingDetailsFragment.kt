package com.example.animelistings.listingdetails

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.animelistings.databinding.FragmentListingDetailsBinding

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class ListingDetailsFragment : Fragment() {

    private var _binding: FragmentListingDetailsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val detailsViewModel by lazy {
        val id = ListingDetailsFragmentArgs.fromBundle(requireArguments()).id
        ViewModelProvider(
            this,
            ListingDetailsViewModel.Factory(id, requireActivity().application)
        )[ListingDetailsViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentListingDetailsBinding.inflate(inflater, container, false)
        binding.run {
            viewModel = detailsViewModel
            lifecycleOwner = viewLifecycleOwner
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}