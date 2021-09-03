package com.transact.collen.assignment.ui

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.transact.collen.assignment.R
import com.transact.collen.assignment.databinding.MyFlagsCollectionBinding
import com.transact.collen.assignment.ui.adapter.DividerDecoration
import com.transact.collen.assignment.ui.adapter.FlagsListAdapter
import com.transact.collen.assignment.viewmodels.CountryFlagViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

/**
 *
 */
class MyFlagsFragment : Fragment() {

    val countryFlagViewModel: CountryFlagViewModel by sharedViewModel()
    lateinit var binding: MyFlagsCollectionBinding
    lateinit var flagsAdapter: FlagsListAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.my_flags_collection, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        flagsAdapter = FlagsListAdapter()
        binding = MyFlagsCollectionBinding.bind(view)
        binding.viewModel = countryFlagViewModel
        setUpCountryFlagsRecyclerview()
        setUpViewBindings()
    }

    private fun setUpCountryFlagsRecyclerview() {
        binding.idFlagsList.apply {
            this.adapter = flagsAdapter
            layoutManager = LinearLayoutManager(this.context, LinearLayoutManager.VERTICAL, false)
            addItemDecoration(DividerDecoration(this.context, Color.WHITE, 16f))
        }
    }

    private fun setUpViewBindings() {

        countryFlagViewModel.filterMutableLiveData.observe(viewLifecycleOwner, {
            flagsAdapter.filter.filter(it)
        })

        countryFlagViewModel.flagCollectionLivedata.observe(viewLifecycleOwner, {
            flagsAdapter.swapItems(it)
        })

        countryFlagViewModel.resetFilterMutableLiveData.observe(viewLifecycleOwner, {
            flagsAdapter.swapItems((countryFlagViewModel.flagCollectionLivedata.value))
        })
    }

    override fun onPause() {
        super.onPause()
        countryFlagViewModel.resetFilter()
    }

    override fun onResume() {
        super.onResume()
        activity?.title = getString(R.string.my_flag_collection_fragment_label)
        activity?.findViewById<ImageButton>(R.id.id_my_flags_btn)?.visibility =
            View.GONE
        activity?.findViewById<SearchView>(R.id.id_search_flags_collection)?.visibility =
            View.VISIBLE
        activity?.findViewById<FloatingActionButton>(R.id.button_add_flag_to_collection)?.visibility =
            View.GONE
    }
}