package dev.dmanluc.marvelmobiletest.presentation.characters

import CoroutineTestRule
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import androidx.test.filters.SmallTest
import arrow.core.left
import arrow.core.right
import dev.dmanluc.marvelmobiletest.data.remote.utils.observeForTesting
import dev.dmanluc.marvelmobiletest.domain.model.ApiError
import dev.dmanluc.marvelmobiletest.domain.model.Character
import dev.dmanluc.marvelmobiletest.domain.model.PagingLoadTrackingState
import dev.dmanluc.marvelmobiletest.domain.usecase.GetCharactersUseCase
import dev.dmanluc.marvelmobiletest.utils.Event
import dev.dmanluc.marvelmobiletest.utils.MockDataProvider
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import runBlockingTest

/**
 * @author Daniel Manrique Lucas <dmanluc91@gmail.com>
 * @version 1
 */
@RunWith(JUnit4::class)
@ExperimentalCoroutinesApi
@SmallTest
class CharactersFragmentViewModelTest {

    @get:Rule
    val coroutineTestRule = CoroutineTestRule()

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var charactersUseCase: GetCharactersUseCase
    private lateinit var charactersViewModel: CharactersFragmentViewModel

    @Before
    fun setUp() {
        charactersUseCase = mockk()
    }

    @Test
    fun `characters requested with no error found when ViewModel is created`() {
        coroutineTestRule.runBlockingTest {
            val mockCharacters = MockDataProvider.createMockCharacterList()
            val viewStateObserver = mockk<Observer<Event<CharactersViewState>>>(relaxed = true)

            coEvery {
                charactersUseCase.invoke(any())
            } returns flowOf(mockCharacters.right())

            charactersViewModel = CharactersFragmentViewModel(
                charactersUseCase,
                coroutineTestRule.testDispatcherProvider
            )

            charactersViewModel.charactersViewSateLiveData.observeForTesting(viewStateObserver) {
                verify {
                    val charactersLoadedViewState = CharactersViewState.CharactersLoaded(
                        PagingLoadTrackingState.Refresh(mockCharacters)
                    )
                    viewStateObserver.onChanged(Event(charactersLoadedViewState))
                }
            }
        }
    }

    @Test
    fun `characters requested with no error found when ViewModel is created and return empty list`() {
        coroutineTestRule.runBlockingTest {
            val mockEmptyList = emptyList<Character>()
            val viewStateObserver = mockk<Observer<Event<CharactersViewState>>>(relaxed = true)

            coEvery {
                charactersUseCase.invoke(any())
            } returns flowOf(mockEmptyList.right())

            charactersViewModel = CharactersFragmentViewModel(
                charactersUseCase,
                coroutineTestRule.testDispatcherProvider
            )

            charactersViewModel.charactersViewSateLiveData.observeForTesting(viewStateObserver) {
                verify {
                    val emptyCharactersLoadedViewState = CharactersViewState.EmptyCharactersLoaded
                    viewStateObserver.onChanged(Event(emptyCharactersLoadedViewState))
                }
            }
        }
    }

    @Test
    fun `characters requested with connectivity found when ViewModel is created`() {
        coroutineTestRule.runBlockingTest {
            val exception = Exception("No internet")
            val apiError = ApiError.NetworkError(exception)
            val viewStateObserver = mockk<Observer<Event<CharactersViewState>>>(relaxed = true)

            coEvery {
                charactersUseCase.invoke(any())
            } returns flowOf(apiError.left())

            charactersViewModel = CharactersFragmentViewModel(
                charactersUseCase,
                coroutineTestRule.testDispatcherProvider
            )

            charactersViewModel.charactersViewSateLiveData.observeForTesting(viewStateObserver) {
                verify {
                    val errorNoConnectivityState = CharactersViewState.ErrorNoConnectivityOnRefresh
                    viewStateObserver.onChanged(Event(errorNoConnectivityState))
                }
            }
        }
    }

    @Test
    fun `characters requested with http error found when ViewModel is created`() {
        coroutineTestRule.runBlockingTest {
            val apiError = ApiError.HttpError(400, "Unauthorised")
            val viewStateObserver = mockk<Observer<Event<CharactersViewState>>>(relaxed = true)

            coEvery {
                charactersUseCase.invoke(any())
            } returns flowOf(apiError.left())

            charactersViewModel = CharactersFragmentViewModel(
                charactersUseCase,
                coroutineTestRule.testDispatcherProvider
            )

            charactersViewModel.charactersViewSateLiveData.observeForTesting(viewStateObserver) {
                verify {
                    val errorState = CharactersViewState.ErrorLoadingCharactersOnRefresh(apiError)
                    viewStateObserver.onChanged(Event(errorState))
                }
            }
        }
    }

    @Test
    fun `load paginated characters with no error found`() {
        coroutineTestRule.runBlockingTest {
            val mockCharacters = MockDataProvider.createMockCharacterList()
            val viewStateObserver = mockk<Observer<Event<CharactersViewState>>>(relaxed = true)

            coEvery {
                charactersUseCase.invoke(any())
            } returns flowOf(mockCharacters.right())

            charactersViewModel = CharactersFragmentViewModel(
                charactersUseCase,
                coroutineTestRule.testDispatcherProvider
            ).apply {
                loadCharacters(20)
            }

            charactersViewModel.charactersViewSateLiveData.observeForTesting(viewStateObserver) {
                verify {
                    val charactersLoadedViewState = CharactersViewState.CharactersLoaded(
                        PagingLoadTrackingState.Append(mockCharacters)
                    )
                    viewStateObserver.onChanged(Event(charactersLoadedViewState))
                }
            }
        }
    }

    @Test
    fun `load paginated characters with connectivity error found`() {
        coroutineTestRule.runBlockingTest {
            val exception = Exception("No internet")
            val apiError = ApiError.NetworkError(exception)
            val viewStateObserver = mockk<Observer<Event<CharactersViewState>>>(relaxed = true)
            val fromOffset = 35

            coEvery {
                charactersUseCase.invoke(any())
            } returns flowOf(apiError.left())

            charactersViewModel = CharactersFragmentViewModel(
                charactersUseCase,
                coroutineTestRule.testDispatcherProvider
            ).apply {
                loadCharacters(fromOffset)
            }

            charactersViewModel.charactersViewSateLiveData.observeForTesting(viewStateObserver) {
                verify {
                    val errorState = CharactersViewState.ErrorNoConnectivityOnPaging(fromOffset)
                    viewStateObserver.onChanged(Event(errorState))
                }
            }
        }
    }

    @Test
    fun `load paginated characters with http error found`() {
        coroutineTestRule.runBlockingTest {
            val apiError = ApiError.HttpError(400, "Unauthorised")
            val viewStateObserver = mockk<Observer<Event<CharactersViewState>>>(relaxed = true)
            val fromOffset = 35

            coEvery {
                charactersUseCase.invoke(any())
            } returns flowOf(apiError.left())

            charactersViewModel = CharactersFragmentViewModel(
                charactersUseCase,
                coroutineTestRule.testDispatcherProvider
            ).apply {
                loadCharacters(fromOffset)
            }

            charactersViewModel.charactersViewSateLiveData.observeForTesting(viewStateObserver) {
                verify {
                    val errorState =
                        CharactersViewState.ErrorLoadingCharactersOnPaging(apiError, fromOffset)
                    viewStateObserver.onChanged(Event(errorState))
                }
            }
        }
    }

    @Test
    fun `load paginated characters with no error found when reached end of pagination`() {
        coroutineTestRule.runBlockingTest {
            val mockEmptyCharacters = emptyList<Character>()
            val viewStateObserver = mockk<Observer<Event<CharactersViewState>>>(relaxed = true)

            coEvery {
                charactersUseCase.invoke(any())
            } returns flowOf(mockEmptyCharacters.right())

            charactersViewModel = CharactersFragmentViewModel(
                charactersUseCase,
                coroutineTestRule.testDispatcherProvider
            ).apply {
                loadCharacters(35)
            }

            charactersViewModel.charactersViewSateLiveData.observeForTesting(viewStateObserver) {
                verify {
                    val charactersLoadedViewState = CharactersViewState.CharactersLoaded(
                        PagingLoadTrackingState.EndOfPagination
                    )
                    viewStateObserver.onChanged(Event(charactersLoadedViewState))
                }
            }
        }
    }

}