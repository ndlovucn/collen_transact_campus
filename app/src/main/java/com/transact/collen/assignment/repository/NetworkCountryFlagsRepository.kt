package com.transact.collen.assignment.repository

import android.graphics.BitmapFactory
import com.mywallst.collen.assignment.network.RetrofitImpl
import com.transact.collen.assignment.entities.Flag

private val TAG: String = "NetworkCountryFlagsRepository"

class NetworkCountryFlagsRepository(val retrofit: RetrofitImpl) : ICountryFlagsRepository {
    override suspend fun getFlagForCountryCode(countryCode: String, size: Int): Flag {
        try {
            val response =
                retrofit.create(CountryFlagsService::class.java).getCountryFlags(countryCode, size)
            val bitmap = BitmapFactory.decodeStream(response.byteStream())
            return Flag(countryCode, bitmap)
        } catch (e: Exception) {
            throw e
        }
    }
}