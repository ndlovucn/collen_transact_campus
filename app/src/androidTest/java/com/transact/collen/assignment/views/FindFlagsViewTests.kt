package com.transact.collen.assignment.views

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.transact.collen.assignment.R
import com.transact.collen.assignment.repository.ICountryFlagsRepository
import com.transact.collen.assignment.repository.IDatabaseCountryFlagsRepository
import com.transact.collen.assignment.ui.MainActivity
import com.transact.collen.assignment.utils.CoroutineTestRule
import com.transact.collen.assignment.utils.getTestBitmap
import com.transact.collen.assignment.utils.overrideModule
import com.transact.collen.assignment.viewmodels.CountryFlagViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.context.loadKoinModules
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.koin.test.check.checkModules
import org.koin.test.get
import org.koin.test.mock.MockProviderRule
import org.mockito.Mockito
import kotlin.test.assertNotNull


@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class FindFlagsViewTests : KoinTest {

    @Rule
    @JvmField
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var coroutinesTestRule = CoroutineTestRule()


    @get:Rule
    val mockProvider = MockProviderRule.create { clazz ->
        Mockito.mock(clazz.java)
    }

    lateinit var networkRepo: ICountryFlagsRepository
    lateinit var viewModel: CountryFlagViewModel
    lateinit var dbRepo: IDatabaseCountryFlagsRepository

    @Before
    fun setUp() {
        loadKoinModules(listOf(overrideModule))
        networkRepo = get<ICountryFlagsRepository>()
        viewModel = get<CountryFlagViewModel>()
        dbRepo = get<IDatabaseCountryFlagsRepository>()
    }


    @Test
    fun testGetFlagWorkFlow() = coroutinesTestRule.testDispatcher.runBlockingTest {
        Mockito.`when`(networkRepo.getFlagForCountryCode("za")).thenReturn(
            getTestBitmap()?.first()
        )

        ActivityScenario.launch(MainActivity::class.java)
        assertNotNull(viewModel.countryFlagRepository.getFlagForCountryCode("za"))
        Espresso.onView(withId(R.id.id_flags_search_edit_text))
            .perform(ViewActions.typeText("z"), ViewActions.closeSoftKeyboard())

        // check validation of ISO code
        Espresso.onView(withText("Enter a valid Country ISO code")).check(matches(isDisplayed()))

        Espresso.onView(withId(R.id.id_flags_search_edit_text))
            .perform(ViewActions.clearText(), ViewActions.closeSoftKeyboard())


        Espresso.onView(withId(R.id.id_flags_search_edit_text))
            .perform(ViewActions.typeText("za"), ViewActions.closeSoftKeyboard())
        // only enable add button when we have preview
        Espresso.onView(withId(R.id.button_add_flag_to_collection))
            .check(matches(isNotEnabled()))

        Espresso.onView(withId(R.id.id_button_search)).perform(ViewActions.click())
    }

    @Test
    fun verifyViewModelDependanciesNotNull() {
        assertNotNull(get<CountryFlagViewModel>())
        assertNotNull(get<IDatabaseCountryFlagsRepository>())
        assertNotNull(get<ICountryFlagsRepository>())
    }


    @Test
    fun testAllViewsAreRenderedCorrectly() = coroutinesTestRule.testDispatcher.runBlockingTest {
        ActivityScenario.launch(MainActivity::class.java)
        Espresso.onView(withId(R.id.id_button_search)).check(matches(isDisplayed()))
        Espresso.onView(withId(R.id.id_flags_search_edit_text)).check(matches(isDisplayed()))
        Espresso.onView(withId(R.id.toolbar_title)).check(matches(isDisplayed()))
        Espresso.onView(withId(R.id.toolbar_title)).check(matches(withText("Find a flag")))
        Espresso.onView(withId(R.id.id_flags_search_edit_text))
            .perform(ViewActions.typeText("ZW"), ViewActions.closeSoftKeyboard())
    }

    @Test
    fun testAddFlagToCollection() = coroutinesTestRule.testDispatcher.runBlockingTest {

        ActivityScenario.launch(MainActivity::class.java)

        Espresso.onView(withId(R.id.id_flags_search_edit_text))
            .perform(ViewActions.typeText("za"), ViewActions.closeSoftKeyboard())

        Espresso.onView(withId(R.id.id_button_search)).perform(ViewActions.click())

        Espresso.onView(withId(R.id.button_add_flag_to_collection))
            .perform(ViewActions.click())

        Mockito.verify(dbRepo, Mockito.atLeastOnce()).getAllFlags()
    }


    @Test
    fun checkMVPhierarchy() {
        stopKoin()
        checkModules {
            modules(overrideModule)
        }
    }
}