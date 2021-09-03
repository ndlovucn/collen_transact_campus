package com.transact.collen.assignment.repository

import com.transact.collen.assignment.entities.Flag

interface ICountryFlagsRepository {
    suspend fun getFlagForCountryCode(countryCode: String, size: Int =64): Flag
}