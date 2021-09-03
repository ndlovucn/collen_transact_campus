package com.transact.collen.assignment.repository

import com.transact.collen.assignment.data.FlagEntity
import kotlinx.coroutines.flow.Flow


interface IDatabaseCountryFlagsRepository {

    fun getAllFlags(): Flow<List<FlagEntity>>

    fun insertFlag(flagEntity: FlagEntity)

    fun findById(id: String): Flow<List<FlagEntity>>
}