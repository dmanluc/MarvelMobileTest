package dev.dmanluc.openbankmobiletest.presentation.characters

import dev.dmanluc.openbankmobiletest.domain.model.ApiError
import dev.dmanluc.openbankmobiletest.domain.model.PagingLoadTrackingState

sealed class CharactersViewState {
    object FirstLoading : CharactersViewState()
    object EmptyCharactersLoaded : CharactersViewState()
    class CharactersLoaded(val pagingLoadTrackingState: PagingLoadTrackingState) :
        CharactersViewState()

    class ErrorLoadingCharactersOnRefresh(val apiError: ApiError) : CharactersViewState()
    class ErrorLoadingCharactersOnPaging(val apiError: ApiError, val fromOffset: Int) :
        CharactersViewState()

    object ErrorNoConnectivityOnRefresh : CharactersViewState()
    class ErrorNoConnectivityOnPaging(val fromOffset: Int) : CharactersViewState()
}