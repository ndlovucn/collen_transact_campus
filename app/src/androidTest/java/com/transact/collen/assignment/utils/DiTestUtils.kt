package com.transact.collen.assignment.utils

import com.transact.collen.assignment.repository.ICountryFlagsRepository
import com.transact.collen.assignment.repository.IDatabaseCountryFlagsRepository
import com.transact.collen.assignment.viewmodels.CountryFlagViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import org.mockito.Mockito


val overrideModule = module {
    val mockFlagsNetworkRepository = Mockito.mock(ICountryFlagsRepository::class.java)
    val mockFlagsDbRepository = Mockito.mock(IDatabaseCountryFlagsRepository::class.java)
    fun provideVM(): CountryFlagViewModel {
        return CountryFlagViewModel(mockFlagsNetworkRepository, mockFlagsDbRepository)
    }
    single {mockFlagsNetworkRepository as ICountryFlagsRepository  }
    single {mockFlagsDbRepository as IDatabaseCountryFlagsRepository  }
    viewModel { provideVM() }
}


interface DispatcherProvider {

    fun main(): CoroutineDispatcher = Dispatchers.Main
    fun default(): CoroutineDispatcher = Dispatchers.Default
    fun io(): CoroutineDispatcher = Dispatchers.IO
    fun unconfined(): CoroutineDispatcher = Dispatchers.Unconfined
}
