package com.mywallst.collen.assignment.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.platform.app.InstrumentationRegistry
import com.transact.collen.assignment.dao.CountryFlagsDao
import com.transact.collen.assignment.data.FlagEntity
import com.transact.collen.assignment.database.AppDatabase
import com.transact.collen.assignment.utils.testFlagEntities
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class CountryFlagsDaoTests {

    private lateinit var database: AppDatabase
    private lateinit var countryFlagsDao: CountryFlagsDao

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()


    @Before
    fun createDb() = runBlocking {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        database = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()
        countryFlagsDao = database.countryFlagsDao

        database.countryFlagsDao.insertAll(*testFlagEntities.toTypedArray())
    }


    @After
    fun closeDb() {
        database.close()
    }

    @Test
    fun testGetFavourites() = runBlocking {
        val testFlagEntity = FlagEntity(id = "gb", null)
        countryFlagsDao.insertAll(testFlagEntity)
        ViewMatchers.assertThat(
            countryFlagsDao.findAll().first().size,
            CoreMatchers.equalTo(4)
        )
    }


}