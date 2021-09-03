package com.transact.collen.assignment.di

import android.app.Application
import androidx.room.Room
import com.mywallst.collen.assignment.network.RetrofitImpl
import com.transact.collen.assignment.dao.CountryFlagsDao
import com.transact.collen.assignment.database.AppDatabase
import com.transact.collen.assignment.repository.DatabaseCountryFlagsRepository
import com.transact.collen.assignment.repository.ICountryFlagsRepository
import com.transact.collen.assignment.repository.IDatabaseCountryFlagsRepository
import com.transact.collen.assignment.repository.NetworkCountryFlagsRepository
import com.transact.collen.assignment.viewmodels.CountryFlagViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val repositoryModule = module {

    single {
        NetworkCountryFlagsRepository(RetrofitImpl()) as ICountryFlagsRepository
    }
}


val databaseModule = module {

    fun provideDatabase(application: Application): AppDatabase {
        return Room.databaseBuilder(application, AppDatabase::class.java, "country_flags")
            .fallbackToDestructiveMigration()
            .build()
    }

    fun provideCountryFlagsDao(database: AppDatabase): CountryFlagsDao {
        return database.countryFlagsDao
    }

    single { provideDatabase(get()) }
    single { provideCountryFlagsDao(get()) }

    fun provideCountryDatabaseRepository(dao: CountryFlagsDao): IDatabaseCountryFlagsRepository {
        return DatabaseCountryFlagsRepository(dao)
    }
    single { provideCountryDatabaseRepository(get()) }

    viewModel { CountryFlagViewModel(get(), get()) }
}