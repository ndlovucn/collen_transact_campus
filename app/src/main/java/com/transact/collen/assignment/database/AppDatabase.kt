package com.transact.collen.assignment.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.transact.collen.assignment.dao.CountryFlagsDao
import com.transact.collen.assignment.data.FlagEntity


@Database(entities = arrayOf(FlagEntity::class), version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract val countryFlagsDao: CountryFlagsDao
}