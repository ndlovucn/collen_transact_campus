package com.transact.collen.assignment.utils

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.transact.collen.assignment.data.FlagEntity
import com.transact.collen.assignment.entities.Flag
import java.io.ByteArrayOutputStream
import java.util.*


fun getBytesFromBitmap(bitmap: Bitmap?): ByteArray? {
    if (bitmap != null) {
        val stream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 70, stream)
        return stream.toByteArray()
    }
    return null
}

fun getBitmapFromBitmapByteArray(byteArray: ByteArray?): Bitmap? {
    if (byteArray == null) return null
    return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
}

fun mapFlagModelToFlagEntity(flag: Flag): FlagEntity? {
    flag.flagBitmap
    flag.id
    return (FlagEntity(id = flag.id, flagByte = getBytesFromBitmap(flag.flagBitmap)))
}

fun mapCountryFlagEntityToFlag(flagEntity: FlagEntity): Flag? {
    flagEntity.flagByte ?: return null
    return getBitmapFromBitmapByteArray(flagEntity.flagByte)?.let {
        Flag(
            id = flagEntity.id,
            flagBitmap = it
        )
    }
}

fun isValidCountryIsoCode(countryCode: String): Boolean {
    if (countryCode.length != 2) return false
    return Locale.getISOCountries().toSet().contains(countryCode.toUpperCase())
}


