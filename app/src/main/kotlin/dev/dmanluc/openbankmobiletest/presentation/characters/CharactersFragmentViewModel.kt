package dev.dmanluc.openbankmobiletest.presentation.characters

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dev.dmanluc.openbankmobiletest.domain.model.ApiError
import dev.dmanluc.openbankmobiletest.domain.model.Character
import dev.dmanluc.openbankmobiletest.domain.model.PagingLoadTrackingState
import dev.dmanluc.openbankmobiletest.domain.usecase.GetCharactersUseCase
import dev.dmanluc.openbankmobiletest.presentation.base.BaseViewModel
import dev.dmanluc.openbankmobiletest.utils.AppDispatchers
import dev.dmanluc.openbankmobiletest.utils.Event
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class CharactersFragmentViewModel(
    private val getCharactersUseCase: GetCharactersUseCase,
    private val appDispatchers: AppDispatchers
) : BaseViewModel() {

    private val mutablePagingLoadTrackingStateLiveData = MutableLiveData<PagingLoadTrackingState>()
    val pagingLoadTrackingStateLiveData: LiveData<PagingLoadTrackingState> get() = mutablePagingLoadTrackingStateLiveData

    init {
        loadCharacters()
    }

    fun loadCharacters(pagingOffset: Int = 0) {
        viewModelScope.launch(appDispatchers.io) {
            getCharactersUseCase(pagingOffset).collect { value ->
                value.fold(ifLeft = ::handleError) { characterList ->
                    handleCharactersResult(pagingOffset, characterList)
                }
            }
        }
    }

    private fun handleCharactersResult(pagingOffset: Int, characters: List<Character>) {
        when {
            pagingOffset == 0 -> {
                mutablePagingLoadTrackingStateLiveData.postValue(
                    PagingLoadTrackingState.Refresh(
                        characters
                    )
                )
            }
            characters.isNotEmpty() -> {
                mutablePagingLoadTrackingStateLiveData.postValue(
                    PagingLoadTrackingState.Append(
                        characters
                    )
                )
            }
            else -> {
                mutablePagingLoadTrackingStateLiveData.postValue(PagingLoadTrackingState.EndOfPagination)
            }
        }
    }

    private fun handleError(error: ApiError) {
        when (error) {
            is ApiError.HttpError -> _snackbarErrorWithStringLiteral.postValue(Event(error.body))
            is ApiError.NetworkError -> _snackbarErrorWithStringLiteral.postValue(Event(error.throwable.localizedMessage.orEmpty()))
            is ApiError.UnknownApiError -> _snackbarErrorWithStringLiteral.postValue(Event(error.throwable.localizedMessage.orEmpty()))
        }
    }

}