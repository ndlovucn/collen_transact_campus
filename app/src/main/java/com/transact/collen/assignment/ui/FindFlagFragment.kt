package com.transact.collen.assignment.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import com.google.android.material.button.MaterialButton
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.transact.collen.assignment.R
import com.transact.collen.assignment.databinding.FindAFlagFragmentBinding
import com.transact.collen.assignment.viewmodels.CountryFlagViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class FindFlagFragment : Fragment() {
    val countryFlagViewModel: CountryFlagViewModel by sharedViewModel()
    lateinit var binding: FindAFlagFragmentBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.find_a_flag_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FindAFlagFragmentBinding.bind(view)
        binding.viewModel = countryFlagViewModel

        observeFlagBitmap()
        observeCountryIsoEditTextEntry()
    }

    private fun observeFlagBitmap() {
        countryFlagViewModel.flagBlobLiveData.observe(viewLifecycleOwner, {
            binding.idFlagPreview.setImageBitmap(it.flagBitmap)
        })
    }

    private fun observeCountryIsoEditTextEntry() {
        countryFlagViewModel.countryIsoErrorText.observe(viewLifecycleOwner, {
            if (it != null && it) {
                binding.textInputLayout.error = getString(R.string.error_invalid_country_code)
                binding.idButtonSearch.isEnabled = false
            } else {
                binding.textInputLayout.error = null
                binding.idButtonSearch.isEnabled = true
            }
        })
    }


    override fun onResume() {
        super.onResume()
        activity?.findViewById<SearchView>(R.id.id_search_flags_collection)?.visibility = View.GONE
        activity?.findViewById<MaterialButton>(R.id.id_button_search)?.visibility = View.VISIBLE
        activity?.findViewById<FloatingActionButton>(R.id.button_add_flag_to_collection)?.visibility =
            View.VISIBLE
        activity?.findViewById<ImageButton>(R.id.id_my_flags_btn)?.visibility =
            View.VISIBLE
        activity?.title = getString(R.string.find_flag_fragment_label)
    }
}