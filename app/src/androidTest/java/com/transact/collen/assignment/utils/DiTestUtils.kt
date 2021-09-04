package com.transact.collen.assignment.utils

import com.transact.collen.assignment.repository.ICountryFlagsRepository
import com.transact.collen.assignment.repository.IDatabaseCountryFlagsRepository
import com.transact.collen.assignment.viewmodels.CountryFlagViewModel
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


