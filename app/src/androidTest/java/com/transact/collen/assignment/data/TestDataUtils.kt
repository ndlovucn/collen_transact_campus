package com.transact.collen.assignment.utils

import android.content.Context
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.internal.inject.InstrumentationContext
import androidx.test.platform.app.InstrumentationRegistry
import com.transact.collen.assignment.R
import com.transact.collen.assignment.data.FlagEntity
import com.transact.collen.assignment.entities.Flag
import org.koin.core.Koin
import java.io.IOException

val testFlagEntities = arrayListOf(
    FlagEntity(id = "za", null),
    FlagEntity(id = "ie", null),
    FlagEntity(id = "zw", null)
)

private const val TESTING = "TESTING"
var Koin.isTesting: Boolean
    get() = getProperty(TESTING, false)
    set(value) {
        setProperty(TESTING, value)
    }


fun getTestBitmap(): MutableList<Flag>? {
    val bitmap = fetchBitmapAsset("be.png")
    return mutableListOf(
        Flag(id = "za", bitmap),
        Flag(id = "ie", bitmap),
        Flag(id = "zw", bitmap)
    )
}

fun fetchBitmapAsset(assetPath: String): Bitmap {
    try {
        var context: Context = ApplicationProvider.getApplicationContext()
        val inputStream =
            context.assets.open(assetPath)
        return BitmapFactory.decodeStream(inputStream)
    } catch (e: IOException) {
        throw RuntimeException(e)
    }
}