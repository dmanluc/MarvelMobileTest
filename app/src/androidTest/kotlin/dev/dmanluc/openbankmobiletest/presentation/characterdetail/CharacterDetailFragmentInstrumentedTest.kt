package dev.dmanluc.openbankmobiletest.presentation.characterdetail

import android.app.Instrumentation
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.Intents.intending
import androidx.test.espresso.intent.matcher.IntentMatchers.hasAction
import androidx.test.espresso.intent.matcher.IntentMatchers.hasData
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import dev.dmanluc.openbankmobiletest.R
import dev.dmanluc.openbankmobiletest.domain.model.CharacterUrlType
import dev.dmanluc.openbankmobiletest.presentation.utils.isVisible
import dev.dmanluc.openbankmobiletest.utils.MockDataProvider
import dev.dmanluc.openbankmobiletest.utils.enforceHttps
import io.mockk.mockk
import org.hamcrest.CoreMatchers.allOf
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 *
 *
 */
@RunWith(AndroidJUnit4::class)
@LargeTest
class CharacterDetailFragmentInstrumentedTest {

    private val mockCharacters = MockDataProvider.createMockCharacterList()

    private lateinit var fragment: CharacterDetailFragment

    @Before
    fun setUp() {
        Intents.init()
    }

    @Test
    fun onClickOnCharacterDetailsLink_shouldOpenWebLink() {
        launchFragment()

        val uriLink = Uri.parse(
            mockCharacters.first().urls?.firstOrNull { it.type == CharacterUrlType.DETAIL }?.url.orEmpty()
                .enforceHttps()
        )

        val expectedIntent = allOf(hasAction(Intent.ACTION_VIEW), hasData(uriLink))
        intending(expectedIntent).respondWith(Instrumentation.ActivityResult(0, null))

        onView(withId(R.id.characterDetailAdditionalInfoDetailRes)).isVisible()
        onView(withId(R.id.characterDetailAdditionalInfoDetailRes)).perform(click())

        intended(expectedIntent)
    }

    @Test
    fun onClickOnCharacterWikiLink_shouldOpenWebLink() {
        launchFragment()

        val uriLink = Uri.parse(
            mockCharacters.first().urls?.firstOrNull { it.type == CharacterUrlType.WIKI }?.url.orEmpty()
                .enforceHttps()
        )

        val expectedIntent = allOf(hasAction(Intent.ACTION_VIEW), hasData(uriLink))
        intending(expectedIntent).respondWith(Instrumentation.ActivityResult(0, null))

        onView(withId(R.id.characterDetailAdditionalInfoWikiRes)).isVisible()
        onView(withId(R.id.characterDetailAdditionalInfoWikiRes)).perform(click())

        intended(expectedIntent)
    }

    @Test
    fun onClickOnCharacterComicsLink_shouldOpenWebLink() {
        launchFragment()

        val uriLink = Uri.parse(
            mockCharacters.first().urls?.firstOrNull { it.type == CharacterUrlType.COMIC }?.url.orEmpty()
                .enforceHttps()
        )

        val expectedIntent = allOf(hasAction(Intent.ACTION_VIEW), hasData(uriLink))
        intending(expectedIntent).respondWith(Instrumentation.ActivityResult(0, null))

        onView(withId(R.id.characterDetailAdditionalInfoComicsRes)).isVisible()
        onView(withId(R.id.characterDetailAdditionalInfoComicsRes)).perform(click())

        intended(expectedIntent)
    }

    @After
    fun tearDown() {
        Intents.release()
    }

    private fun launchFragment(): NavController {
        val mockNavController = mockk<NavController>(relaxed = true)
        val characterDetailsScreenScenario =
            launchFragmentInContainer<CharacterDetailFragment>(fragmentArgs = Bundle().apply {
                putParcelable("character", mockCharacters.first())
                putString("title", mockCharacters.first().name)
            }, themeResId = R.style.Theme_OpenbankMobileTest)

        characterDetailsScreenScenario.onFragment { fragment ->
            this.fragment = fragment

            Navigation.setViewNavController(fragment.requireView(), mockNavController)
        }

        return mockNavController
    }

}
