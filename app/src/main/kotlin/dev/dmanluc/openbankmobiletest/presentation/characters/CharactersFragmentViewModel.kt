package dev.dmanluc.openbankmobiletest.presentation.characters

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.dmanluc.openbankmobiletest.domain.model.ApiError
import dev.dmanluc.openbankmobiletest.domain.model.Character
import dev.dmanluc.openbankmobiletest.domain.model.PagingLoadTrackingState
import dev.dmanluc.openbankmobiletest.domain.usecase.GetCharactersUseCase
import dev.dmanluc.openbankmobiletest.utils.DispatcherProvider
import dev.dmanluc.openbankmobiletest.utils.Event
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch

/**
 * @author   Daniel Manrique Lucas <dmanluc91@gmail.com>
 * @version  1
 *
 *  Viewmodel for characters fragment which exposes characters screen view state
 *
 */
class CharactersFragmentViewModel(
    private val getCharactersUseCase: GetCharactersUseCase,
    private val appDispatchers: DispatcherProvider
) : ViewModel() {

    private val mutableCharactersViewStateLiveData: MutableLiveData<Event<CharactersViewState>> =
        MutableLiveData()
    val charactersViewSateLiveData: LiveData<Event<CharactersViewState>> get() = mutableCharactersViewStateLiveData

    init {
        mutableCharactersViewStateLiveData.value = Event(CharactersViewState.FirstLoading)
        loadCharacters()
    }

    fun loadCharacters(pagingOffset: Int = 0, forceRefresh: Boolean = false) {
        viewModelScope.launch(appDispatchers.main()) {
            getCharactersUseCase(pagingOffset, forceRefresh)
                .flowOn(appDispatchers.io())
                .collect { value ->
                    value.fold(ifLeft = { apiError ->
                        handleError(
                            pagingOffset,
                            apiError
                        )
                    }) { characterList ->
                        handleCharactersResult(pagingOffset, characterList)
                    }
                }
        }
    }

    fun refreshCharacters() {
        loadCharacters(forceRefresh = true)
    }

    private fun handleCharactersResult(pagingOffset: Int, characters: List<Character>) {
        when {
            pagingOffset == 0 -> {
                if (characters.isEmpty()) {
                    val newStateEvent = Event(CharactersViewState.EmptyCharactersLoaded)
                    mutableCharactersViewStateLiveData.value = newStateEvent
                } else {
                    val newStateEvent = Event(
                        CharactersViewState.CharactersLoaded(
                            PagingLoadTrackingState.Refresh(characters)
                        )
                    )
                    mutableCharactersViewStateLiveData.value = newStateEvent
                }
            }
            characters.isNotEmpty() -> {
                val newStateEvent = Event(
                    CharactersViewState.CharactersLoaded(
                        PagingLoadTrackingState.Append(characters)
                    )
                )
                mutableCharactersViewStateLiveData.value = newStateEvent
            }
            else -> {
                val newStateEvent = Event(
                    CharactersViewState.CharactersLoaded(
                        PagingLoadTrackingState.EndOfPagination
                    )
                )
                mutableCharactersViewStateLiveData.value = newStateEvent
            }
        }
    }

    private fun handleError(pagingOffset: Int, error: ApiError) {
        when (error) {
            is ApiError.NetworkError -> {
                val newErrorEvent = if (pagingOffset != 0) {
                    Event(CharactersViewState.ErrorNoConnectivityOnPaging(pagingOffset))
                } else {
                    Event(CharactersViewState.ErrorNoConnectivityOnRefresh)
                }
                mutableCharactersViewStateLiveData.value = newErrorEvent
            }
            else -> {
                val newErrorEvent = if (pagingOffset != 0) {
                    Event(CharactersViewState.ErrorLoadingCharactersOnPaging(error, pagingOffset))
                } else {
                    Event(CharactersViewState.ErrorLoadingCharactersOnRefresh(error))
                }
                mutableCharactersViewStateLiveData.value = newErrorEvent
            }
        }
    }

}