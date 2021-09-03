package com.transact.collen.assignment.repository

import com.transact.collen.assignment.dao.CountryFlagsDao
import com.transact.collen.assignment.data.FlagEntity
import kotlinx.coroutines.flow.Flow

class DatabaseCountryFlagsRepository(private val dao: CountryFlagsDao) :
    IDatabaseCountryFlagsRepository {
    override fun getAllFlags(): Flow<List<FlagEntity>> = dao.findAll()

    override fun insertFlag(flagEntity: FlagEntity) = dao.insertAll(flagEntity)

    override fun findById(id: String): Flow<List<FlagEntity>> = dao.findById(id)
}