package com.example.animelistings.listings

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.animelistings.adapter.ItemDecorations
import com.example.animelistings.adapter.ListingsAdapter
import com.example.animelistings.databinding.FragmentListingsBinding

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class ListingsFragment : Fragment() {

    private var _binding: FragmentListingsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val viewModel by lazy {
        ViewModelProvider(
            this,
            ListingsViewModel.Factory(requireActivity().application)
        )[ListingsViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentListingsBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        val listingsAdapter = ListingsAdapter(ListingsAdapter.OnClickListener {
            findNavController().navigate(ListingsFragmentDirections.actionListingsFragmentToListingDetailsFragment(it.id))
        })
        binding.listingsRecyclerview.adapter = listingsAdapter
        binding.listingsRecyclerview.addItemDecoration(ItemDecorations.VerticalSpacing(30))
        viewModel.anime.observe(viewLifecycleOwner, {
            listingsAdapter.submitList(it)
        })

        return binding.root

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}