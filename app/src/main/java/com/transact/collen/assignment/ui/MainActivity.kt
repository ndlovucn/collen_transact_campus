package com.transact.collen.assignment.ui

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import com.transact.collen.assignment.R
import com.transact.collen.assignment.databinding.ActivityMainBinding
import com.transact.collen.assignment.viewmodels.CountryFlagViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity(), SearchView.OnQueryTextListener,
    SearchView.OnCloseListener {

    val countryFlagViewModel: CountryFlagViewModel by viewModel()
    var toolbar: Toolbar? = null
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.viewModel = countryFlagViewModel

        setSupportActionBar(findViewById(R.id.toolbar))
        toolbar = binding.toolbar

        val host =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment?
                ?: return
        val navController = setupNavController(host)

        binding.idMyFlagsBtn.setOnClickListener {
            it.visibility = View.GONE
            binding.buttonAddFlagToCollection.visibility = View.GONE
            navController.navigate(FindFlagFragmentDirections.actionFindFlagToMyFlagCollection())
        }

        binding.idSearchFlagsCollection.setOnQueryTextListener(this)
        binding.idSearchFlagsCollection.setOnCloseListener(this)
        setUpLiveDataObservers()
    }

    private fun setupNavController(host: NavHostFragment): NavController {
        val navController = host.navController
        val appBarConfiguration = AppBarConfiguration.Builder(navController.graph).build()

        toolbar?.let {
            NavigationUI.setupWithNavController(it, navController, appBarConfiguration)
        }
        return navController
    }

    private fun setUpLiveDataObservers() {
        countryFlagViewModel.isFlagSavedToCollectionLiveData.observe(this, {
            if (it) Toast.makeText(
                this,
                getString(R.string.flag_added_to_collection),
                Toast.LENGTH_SHORT
            ).show()
        })

        countryFlagViewModel.flagBlobLiveData.observe(this, {
            binding.buttonAddFlagToCollection.isEnabled = it != null
        })
    }

    override fun setTitle(title: CharSequence?) {
        super.setTitle(title)
        binding.toolbarTitle.text = title
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        query?.let { countryFlagViewModel.filterFlagBy(query = it) }
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        newText?.let { countryFlagViewModel.filterFlagBy(query = it) }
        return true
    }

    override fun onClose(): Boolean {
        countryFlagViewModel.resetFilter()
        return true
    }
}