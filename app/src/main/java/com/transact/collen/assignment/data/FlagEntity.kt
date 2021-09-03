package com.transact.collen.assignment.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "flag")
data class FlagEntity(
    @PrimaryKey
    val id: String,
    @ColumnInfo(typeAffinity = ColumnInfo.BLOB, name = "flag")
    var flagByte: ByteArray? = null
)
