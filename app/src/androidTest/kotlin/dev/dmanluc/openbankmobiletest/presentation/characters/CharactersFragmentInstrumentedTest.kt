package dev.dmanluc.openbankmobiletest.presentation.characters

import android.view.View
import androidx.annotation.StringRes
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.FragmentNavigator
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.ViewAssertion
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.idling.CountingIdlingResource
import androidx.test.espresso.matcher.BoundedMatcher
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import arrow.core.left
import arrow.core.right
import com.google.android.material.snackbar.SnackbarContentLayout
import dev.dmanluc.openbankmobiletest.R
import dev.dmanluc.openbankmobiletest.domain.model.ApiError
import dev.dmanluc.openbankmobiletest.domain.model.Character
import dev.dmanluc.openbankmobiletest.domain.repository.CharactersRepository
import dev.dmanluc.openbankmobiletest.domain.usecase.GetCharactersUseCase
import dev.dmanluc.openbankmobiletest.espressoRecyclerViewActions.RecyclerViewItemCountAssertion.Companion.withItemCount
import dev.dmanluc.openbankmobiletest.presentation.utils.isGone
import dev.dmanluc.openbankmobiletest.presentation.utils.isVisible
import dev.dmanluc.openbankmobiletest.utils.DispatcherProvider
import dev.dmanluc.openbankmobiletest.utils.MockDataProvider
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.flowOf
import org.hamcrest.CoreMatchers
import org.hamcrest.CoreMatchers.allOf
import org.hamcrest.CoreMatchers.not
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.greaterThan
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module
import org.koin.test.AutoCloseKoinTest

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 *
 *
 */
@RunWith(AndroidJUnit4::class)
@LargeTest
class CharactersFragmentInstrumentedTest : AutoCloseKoinTest() {

    private val mockCharacters = MockDataProvider.createMockCharacterList()

    private val dispatchersProvider = object : DispatcherProvider {
        override fun io(): CoroutineDispatcher {
            return super.main()
        }
    }

    private var mockCharactersRepository = mockk<CharactersRepository>()

    private var componentIdlingResource: CountingIdlingResource? = null


    private lateinit var fragment: CharactersFragment

    @Before
    fun setUp() {
        startKoin {
            modules(listOf(module {
                factory { GetCharactersUseCase(mockCharactersRepository) }
                viewModel { CharactersFragmentViewModel(get(), dispatchersProvider) }
            }))
        }
    }

    @Test
    fun loadCharactersOnFragmentLaunch_whenSuccessFetchingNotEmptyData_shouldShowCharactersList() {
        coEvery {
            mockCharactersRepository.getCharacters(any(), any())
        } returns flowOf(mockCharacters.right())

        launchFragment()

        onView(withId(R.id.lottieLoadingCharacters)).isGone()
        onView(withId(R.id.charactersSwipeRefreshLayout)).check(matches(not(ViewActions.swipeDown())))
        onView(withId(R.id.errorView)).isGone()
        onView(withId(R.id.characterRecyclerList)).check(withItemCount(mockCharacters.size))
    }

    @Test
    fun loadCharactersOnFragmentLaunch_whenSuccessFetchingButEmptyData_shouldShowEmptyView() {
        coEvery {
            mockCharactersRepository.getCharacters(any(), any())
        } returns flowOf(emptyList<Character>().right())

        launchFragment()

        onView(withId(R.id.characterRecyclerList)).check(withItemCount(0))
        onView(withId(R.id.lottieLoadingCharacters)).isGone()
        onView(withId(R.id.charactersSwipeRefreshLayout)).check(matches(not(isSwipeLayoutRefreshing())))
        onView(withId(R.id.errorView)).isVisible()
        onView(withId(R.id.errorText)).check(matches(withText(R.string.empty_characters_view_text)))
    }

    @Test
    fun loadCharactersOnFragmentLaunch_whenConnectivityError_shouldShowErrorView() {
        val connectivityApiError = ApiError.NetworkError(Exception("No Internet"))

        coEvery {
            mockCharactersRepository.getCharacters(any(), any())
        } returns flowOf(connectivityApiError.left())

        launchFragment()

        onView(withId(R.id.characterRecyclerList)).check(withItemCount(0))
        onView(withId(R.id.lottieLoadingCharacters)).isGone()
        onView(withId(R.id.charactersSwipeRefreshLayout)).check(matches(not(isSwipeLayoutRefreshing())))
        onView(withId(R.id.errorView)).isVisible()
        onView(withId(R.id.errorText)).check(matches(withText(R.string.no_internet_connection_text)))
    }

    @Test
    fun loadCharactersOnFragmentLaunch_whenApiError_shouldShowErrorView() {
        val apiError = ApiError.HttpError(400, "Error")

        coEvery {
            mockCharactersRepository.getCharacters(any(), any())
        } returns flowOf(apiError.left())

        launchFragment()

        onView(withId(R.id.characterRecyclerList)).check(withItemCount(0))
        onView(withId(R.id.lottieLoadingCharacters)).isGone()
        onView(withId(R.id.charactersSwipeRefreshLayout)).check(matches(not(isSwipeLayoutRefreshing())))
        onView(withId(R.id.errorView)).isVisible()
        onView(withId(R.id.errorText)).check(matches(withText(R.string.error_fetching_characters_text)))
    }

    @Test
    fun refreshCharactersOnSwipe_whenSuccessFetchingNotEmptyDataAndPreviousEmptyScreen_shouldUpdateCharactersList() {
        coEvery {
            mockCharactersRepository.getCharacters(any(), any())
        } returns flowOf(emptyList<Character>().right()) andThen flowOf(mockCharacters.right())

        launchFragment()

        onView(withId(R.id.lottieLoadingCharacters)).isGone()
        onView(withId(R.id.errorView)).isVisible()
        onView(withId(R.id.errorText)).check(matches(withText(R.string.empty_characters_view_text)))
        onView(withId(R.id.charactersSwipeRefreshLayout)).perform(ViewActions.swipeDown())
        onView(withId(R.id.errorView)).isGone()
        onView(withId(R.id.characterRecyclerList)).check(withItemCount(mockCharacters.size))
    }

    @Test
    fun refreshCharactersOnSwipe_whenErrorFetchingDataAndPreviousEmptyScreen_shouldShowErrorView() {
        val apiError = ApiError.HttpError(400, "Error")

        coEvery {
            mockCharactersRepository.getCharacters(any(), any())
        } returns flowOf(emptyList<Character>().right()) andThen flowOf(apiError.left())

        launchFragment()

        onView(withId(R.id.lottieLoadingCharacters)).isGone()
        onView(withId(R.id.errorView)).isVisible()
        onView(withId(R.id.errorText)).check(matches(withText(R.string.empty_characters_view_text)))
        onView(withId(R.id.charactersSwipeRefreshLayout)).perform(ViewActions.swipeDown())
        onView(withId(R.id.errorView)).isVisible()
        onView(withId(R.id.errorText)).check(matches(withText(R.string.error_fetching_characters_text)))
        onView(withId(R.id.characterRecyclerList)).check(withItemCount(0))
    }

    @Test
    fun refreshCharactersOnSwipe_whenSuccessFetchingDataAndPreviousConnectivityErrorScreen_shouldShowCharacters() {
        val connectivityApiError = ApiError.NetworkError(Exception("No Internet"))

        coEvery {
            mockCharactersRepository.getCharacters(any(), any())
        } returns flowOf(connectivityApiError.left()) andThen flowOf(mockCharacters.right())

        launchFragment()

        onView(withId(R.id.lottieLoadingCharacters)).isGone()
        onView(withId(R.id.errorView)).isVisible()
        onView(withId(R.id.errorText)).check(matches(withText(R.string.no_internet_connection_text)))
        onView(withId(R.id.charactersSwipeRefreshLayout)).perform(ViewActions.swipeDown())
        onView(withId(R.id.errorView)).isGone()
        onView(withId(R.id.characterRecyclerList)).check(withItemCount(mockCharacters.size))
    }

    @Test
    fun refreshCharactersOnSwipe_whenErrorFetchingDataAndPreviousConnectivityErrorScreen_shouldShowErrorView() {
        val connectivityApiError = ApiError.NetworkError(Exception("No Internet"))
        val apiError = ApiError.HttpError(400, "Error")

        coEvery {
            mockCharactersRepository.getCharacters(any(), any())
        } returns flowOf(connectivityApiError.left()) andThen flowOf(apiError.left())

        launchFragment()

        onView(withId(R.id.lottieLoadingCharacters)).isGone()
        onView(withId(R.id.errorView)).isVisible()
        onView(withId(R.id.errorText)).check(matches(withText(R.string.no_internet_connection_text)))
        onView(withId(R.id.charactersSwipeRefreshLayout)).perform(ViewActions.swipeDown())
        onView(withId(R.id.errorView)).isVisible()
        onView(withId(R.id.errorText)).check(matches(withText(R.string.error_fetching_characters_text)))
        onView(withId(R.id.characterRecyclerList)).check(withItemCount(0))
    }

    @Test
    fun refreshCharactersOnSwipe_whenSuccessFetchingDataAndPreviousApiErrorScreen_shouldShowCharacters() {
        val apiError = ApiError.HttpError(400, "Error")

        coEvery {
            mockCharactersRepository.getCharacters(any(), any())
        } returns flowOf(apiError.left()) andThen flowOf(mockCharacters.right())

        launchFragment()

        onView(withId(R.id.lottieLoadingCharacters)).isGone()
        onView(withId(R.id.errorView)).isVisible()
        onView(withId(R.id.errorText)).check(matches(withText(R.string.error_fetching_characters_text)))
        onView(withId(R.id.charactersSwipeRefreshLayout)).perform(ViewActions.swipeDown())
        onView(withId(R.id.errorView)).isGone()
        onView(withId(R.id.characterRecyclerList)).check(withItemCount(mockCharacters.size))
    }

    @Test
    fun refreshCharactersOnSwipe_whenErrorFetchingDataAndPreviousApiErrorScreen_shouldShowErrorView() {
        val firstApiError = ApiError.HttpError(400, "Error 1")
        val secondApiError = ApiError.HttpError(400, "Error 2")

        coEvery {
            mockCharactersRepository.getCharacters(any(), any())
        } returns flowOf(firstApiError.left()) andThen flowOf(secondApiError.left())

        launchFragment()

        onView(withId(R.id.lottieLoadingCharacters)).isGone()
        onView(withId(R.id.errorView)).isVisible()
        onView(withId(R.id.errorText)).check(matches(withText(R.string.error_fetching_characters_text)))
        onView(withId(R.id.charactersSwipeRefreshLayout)).perform(ViewActions.swipeDown())
        onView(withId(R.id.errorView)).isVisible()
        onView(withId(R.id.errorText)).check(matches(withText(R.string.error_fetching_characters_text)))
        onView(withId(R.id.characterRecyclerList)).check(withItemCount(0))
    }

    @Test
    fun loadPaginatedCharactersOnScroll_whenSuccessFetchingMoreCharacters_shouldAddCharactersToList() {
        coEvery {
            mockCharactersRepository.getCharacters(any(), any())
        } returns flowOf(mockCharacters.right()) andThen flowOf(mockCharacters.right())

        launchFragment()

        onView(withId(R.id.lottieLoadingCharacters)).isGone()
        onView(withId(R.id.errorView)).isGone()
        onView(withId(R.id.characterRecyclerList)).check(withItemCount(mockCharacters.size))
        onView(withId(R.id.characterRecyclerList)).perform(ViewActions.swipeUp())
        onView(withId(R.id.characterRecyclerList)).check(withItemCount(greaterThan(mockCharacters.size)))
    }

    @Test
    fun loadPaginatedCharactersOnScroll_whenErrorFetchingMoreCharacters_shouldShowSnackbarError() {
        val apiError = ApiError.HttpError(400, "Error")

        coEvery {
            mockCharactersRepository.getCharacters(any(), any())
        } returns flowOf(mockCharacters.right()) andThen flowOf(apiError.left())

        launchFragment()

        onView(withId(R.id.lottieLoadingCharacters)).isGone()
        onView(withId(R.id.errorView)).isGone()
        onView(withId(R.id.characterRecyclerList)).check(withItemCount(mockCharacters.size))
        onView(withId(R.id.characterRecyclerList)).perform(ViewActions.swipeUp())
        onView(withId(R.id.errorView)).isGone()
        onSnackbar(R.string.error_fetching_characters_text)
        onView(withId(R.id.characterRecyclerList)).check(withItemCount(mockCharacters.size))
    }

    @Test
    fun loadPaginatedCharactersOnScroll_whenConnectivityErrorFetchingMoreCharacters_shouldShowSnackbarError() {
        val connectivityError = ApiError.NetworkError(Exception("No Internet"))

        coEvery {
            mockCharactersRepository.getCharacters(any(), any())
        } returns flowOf(mockCharacters.right()) andThen flowOf(connectivityError.left())

        launchFragment()

        onView(withId(R.id.lottieLoadingCharacters)).isGone()
        onView(withId(R.id.errorView)).isGone()
        onView(withId(R.id.characterRecyclerList)).check(withItemCount(mockCharacters.size))
        onView(withId(R.id.characterRecyclerList)).perform(ViewActions.swipeUp())
        onView(withId(R.id.errorView)).isGone()
        onSnackbar(R.string.no_internet_connection_text)
        onView(withId(R.id.characterRecyclerList)).check(withItemCount(mockCharacters.size))
    }

    @Test
    fun retryLoadingPaginatedCharactersOnScroll_whenSuccessFetchingMoreCharactersFromPreviousErrorFetching_shouldAddCharactersToList() {
        val apiError = ApiError.HttpError(400, "Error")

        coEvery {
            mockCharactersRepository.getCharacters(any(), any())
        } returns flowOf(mockCharacters.right()) andThen flowOf(apiError.left()) andThen flowOf(
            mockCharacters.right()
        )

        launchFragment()

        onView(withId(R.id.lottieLoadingCharacters)).isGone()
        onView(withId(R.id.errorView)).isGone()
        onView(withId(R.id.characterRecyclerList)).check(withItemCount(mockCharacters.size))
        onView(withId(R.id.characterRecyclerList)).perform(ViewActions.swipeUp())
        onSnackbar(R.string.error_fetching_characters_text)
        onSnackbarButton(R.string.retry_loading_action_text)
        onView(withId(com.google.android.material.R.id.snackbar_action)).perform(ViewActions.click())
        onView(withId(com.google.android.material.R.id.snackbar_text)).isGone()
        onView(withId(R.id.characterRecyclerList)).check(withItemCount(greaterThan(mockCharacters.size)))
    }

    @Test
    fun retryLoadingPaginatedCharactersOnScroll_whenConnectivityErrorFetchingMoreCharactersFromPreviousErrorFetching_shouldShowSnackbarError() {
        val apiError = ApiError.HttpError(400, "Error")
        val connectivityError = ApiError.NetworkError(Exception("No Internet"))

        coEvery {
            mockCharactersRepository.getCharacters(any(), any())
        } returns flowOf(mockCharacters.right()) andThen flowOf(apiError.left()) andThen flowOf(
            connectivityError.left()
        )

        launchFragment()

        onView(withId(R.id.lottieLoadingCharacters)).isGone()
        onView(withId(R.id.errorView)).isGone()
        onView(withId(R.id.characterRecyclerList)).check(withItemCount(mockCharacters.size))
        onView(withId(R.id.characterRecyclerList)).perform(ViewActions.swipeUp())
        onSnackbar(R.string.no_internet_connection_text)
        onSnackbarButton(R.string.retry_loading_action_text).perform(ViewActions.click())
        onView(withId(R.id.characterRecyclerList)).check(withItemCount(mockCharacters.size))
    }

    @Test
    fun retryLoadingPaginatedCharactersOnScroll_whenErrorFetchingMoreCharactersFromPreviousErrorFetching_shouldShowSnackbarError() {
        val apiError = ApiError.HttpError(400, "Error")

        coEvery {
            mockCharactersRepository.getCharacters(any(), any())
        } returns flowOf(mockCharacters.right()) andThen flowOf(apiError.left()) andThen flowOf(
            apiError.left()
        )

        launchFragment()

        onView(withId(R.id.lottieLoadingCharacters)).isGone()
        onView(withId(R.id.errorView)).isGone()
        onView(withId(R.id.characterRecyclerList)).check(withItemCount(mockCharacters.size))
        onView(withId(R.id.characterRecyclerList)).perform(ViewActions.swipeUp())
        onView(withId(R.id.errorView)).isGone()
        onSnackbar(R.string.error_fetching_characters_text)
        onSnackbarButton(R.string.retry_loading_action_text).perform(ViewActions.click())
        onView(withId(R.id.characterRecyclerList)).check(withItemCount(mockCharacters.size))
    }

    @Test
    fun clickOnCharacter_shouldLaunchCharacterDetailsScreen() {
        coEvery {
            mockCharactersRepository.getCharacters(any(), any())
        } returns flowOf(mockCharacters.right())

        val mockNavController = launchFragment()

        onView(withId(R.id.characterRecyclerList)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                0,
                ViewActions.click()
            )
        )

        verify {
            val characterSelected = mockCharacters.first()
            mockNavController.navigate(
                CharactersFragmentDirections.actionToCharacterDetail(
                    characterSelected, characterSelected.name
                ), any<FragmentNavigator.Extras>()
            )
        }
    }


    @After
    fun tearDown() {
        componentIdlingResource?.let { IdlingRegistry.getInstance().unregister(it) }
    }

    private fun launchFragment(): NavController {
        val mockNavController = mockk<NavController>(relaxed = true)
        val marketProductsScenario =
            launchFragmentInContainer<CharactersFragment>(themeResId = R.style.Theme_OpenbankMobileTest)
        marketProductsScenario.onFragment { fragment ->
            this.fragment = fragment

            componentIdlingResource = fragment.countingIdlingResource
            IdlingRegistry.getInstance().register(componentIdlingResource)

            Navigation.setViewNavController(fragment.requireView(), mockNavController)
        }
        return mockNavController
    }

    private fun isSwipeLayoutRefreshing(): Matcher<View> {
        return object : BoundedMatcher<View, SwipeRefreshLayout>(
            SwipeRefreshLayout::class.java
        ) {

            override fun describeTo(description: Description) {
                description.appendText("is refreshing")
            }

            override fun matchesSafely(view: SwipeRefreshLayout): Boolean {
                return view.isRefreshing
            }
        }
    }

    private fun onSnackbar(@StringRes withText: Int): ViewInteraction {
        return onView(
            CoreMatchers.allOf(
                isDescendantOfA(isAssignableFrom(SnackbarContentLayout::class.java)),
                ViewMatchers.withText(withText)
            )
        )
    }

    private fun onSnackbarButton(@StringRes withText: Int): ViewInteraction {
        return onView(
            allOf(
                isDescendantOfA(isAssignableFrom(SnackbarContentLayout::class.java)),
                ViewMatchers.withText(withText)
            )
        )
    }

}
