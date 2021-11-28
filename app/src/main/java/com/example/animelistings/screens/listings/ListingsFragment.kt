package com.example.animelistings.screens.listings

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.animelistings.adapter.ItemDecorations
import com.example.animelistings.adapter.ListingsAdapter
import com.example.animelistings.databinding.FragmentListingsBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
@AndroidEntryPoint
class ListingsFragment : Fragment() {

    private var _binding: FragmentListingsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    @Inject
    lateinit var listingsViewModel: ListingsViewModel

    private val listingsAdapter by lazy {
        ListingsAdapter(ListingsAdapter.OnClickListener {
            findNavController().navigate(
                ListingsFragmentDirections.actionListingsFragmentToListingDetailsFragment(it.id)
            )
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListingsBinding.inflate(inflater, container, false)
        binding.run {
            lifecycleOwner = viewLifecycleOwner
            viewModel = listingsViewModel
            listingsRecyclerview.adapter = listingsAdapter
            listingsRecyclerview.addItemDecoration(ItemDecorations.VerticalSpacing(30))
        }
        listingsViewModel.anime.observe(viewLifecycleOwner) {
            listingsAdapter.submitList(it)
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}