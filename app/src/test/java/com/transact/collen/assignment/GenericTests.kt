package com.transact.collen.assignment

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.test.core.app.ApplicationProvider
import com.transact.collen.assignment.data.FlagEntity
import com.transact.collen.assignment.entities.Flag
import com.transact.collen.assignment.utils.getBytesFromBitmap
import com.transact.collen.assignment.utils.isValidCountryIsoCode
import com.transact.collen.assignment.utils.mapFlagModelToFlagEntity
import org.junit.After
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.koin.core.context.stopKoin
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import java.io.IOException
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue


@Config(sdk = [28])
@RunWith(RobolectricTestRunner::class)
class GenericTests {


    @Test
    fun testGetBytesFromBitmap() {
        assertNotNull(getBytesFromBitmap(fetchBitmapAsset("be.png")))
    }

    @Test
    fun testMapFlagModelToFlagEntity() {
        val flag = Flag(id = "ca", fetchBitmapAsset("be.png"))
        val flagEntity = mapFlagModelToFlagEntity(flag)
        assertNotNull(flagEntity)
        assertNotNull(flagEntity.flagByte)
        assertTrue(flag.id == flagEntity.id)
    }

    @Test
    fun testIsoValidator(){
        assertEquals(isValidCountryIsoCode(""), false)
        assertEquals(isValidCountryIsoCode("HHH"), false)
        assertEquals(isValidCountryIsoCode("za"), true)
        assertEquals(isValidCountryIsoCode("ZA"), true)
        assertEquals(isValidCountryIsoCode("88"), false)
    }

    @After
    fun cleanUp() {
        stopKoin()
    }
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