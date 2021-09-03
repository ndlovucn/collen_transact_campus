package com.transact.collen.assignment.viewmodels

import android.util.Log
import android.view.View
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableInt
import androidx.lifecycle.*
import com.transact.collen.assignment.entities.Flag
import com.transact.collen.assignment.repository.ICountryFlagsRepository
import com.transact.collen.assignment.repository.IDatabaseCountryFlagsRepository
import com.transact.collen.assignment.utils.isValidCountryIsoCode
import com.transact.collen.assignment.utils.mapCountryFlagEntityToFlag
import com.transact.collen.assignment.utils.mapFlagModelToFlagEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent


private const val TAG = "CountryFlagViewModel"

class CountryFlagViewModel(
    val countryFlagRepository: ICountryFlagsRepository,
    val databaseCountryFlagsRepository: IDatabaseCountryFlagsRepository
) : ViewModel(), KoinComponent {

    private var flagSearchString: String? = null
    var flagBlobLiveData: MutableLiveData<Flag> = MutableLiveData()

    var countryIsoErrorText: MutableLiveData<Boolean?> = MutableLiveData(null)
    val progressDialogVisibility: ObservableInt = ObservableInt(View.GONE)
    val isPreviewAvailableObservableBoolean: ObservableBoolean = ObservableBoolean(false)
    val filterMutableLiveData: MutableLiveData<String> = MutableLiveData()
    val resetFilterMutableLiveData: MutableLiveData<Boolean> = MutableLiveData(false)

    var _isFlagSavedToCollectionLiveData = MutableLiveData(false)
    var isFlagSavedToCollectionLiveData: LiveData<Boolean> = _isFlagSavedToCollectionLiveData

    var flagCollectionLivedata = databaseCountryFlagsRepository.getAllFlags().map {
        it.map {
            mapCountryFlagEntityToFlag(it)
        }
    }.asLiveData()


    fun getFlagForCountryCode(countryCode: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                progressDialogVisibility.set(View.VISIBLE)
                isPreviewAvailableObservableBoolean.set(false)
                val countryFlag = countryFlagRepository.getFlagForCountryCode(countryCode)
                progressDialogVisibility.set(View.GONE)
                flagBlobLiveData.postValue(countryFlag)
                isPreviewAvailableObservableBoolean.set(true)

            } catch (e: Exception) {
                e.message?.let { Log.d(TAG, it) }
            }
        }
    }

    fun saveFlag(flag: Flag) {
        viewModelScope.launch(Dispatchers.IO) {
            val countryFlag = mapFlagModelToFlagEntity(flag)
            countryFlag?.let { databaseCountryFlagsRepository.insertFlag(it) }
            _isFlagSavedToCollectionLiveData.postValue(true)
        }
    }

    fun onGetFlagforId() = flagSearchString?.let { getFlagForCountryCode(it) }

    fun onCountryIsoCodeTextChanged(countryIsoCharSequence: CharSequence) {
        if (countryIsoCharSequence.isEmpty()) {
            countryIsoErrorText.value = null
        } else if (isValidCountryIsoCode(countryIsoCharSequence.toString())) {
            countryIsoErrorText.value = false
            flagSearchString = countryIsoCharSequence.toString().toLowerCase()
        } else {
            countryIsoErrorText.value = true
        }
    }

    fun onAddFlagToCollection() {
        flagBlobLiveData.value?.let { saveFlag(it) }
    }


    fun filterFlagBy(query: String) {
        filterMutableLiveData.value = query
    }

    fun isPreviewAvailable(): Boolean = flagBlobLiveData.value == null


    fun resetFilter() {
        filterMutableLiveData.value = ""
        resetFilterMutableLiveData.value = true
    }
}