package com.transact.collen.assignment.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.transact.collen.assignment.data.FlagEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CountryFlagsDao {
    @Query("SELECT * FROM flag")
    fun findAll(): Flow<List<FlagEntity>>

    @Query("SELECT * FROM flag WHERE id = :ids ",)
    fun findById(ids: String): Flow<List<FlagEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg flagEntity: FlagEntity)

}