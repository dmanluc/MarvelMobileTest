package dev.dmanluc.openbankmobiletest.presentation.characters

import dev.dmanluc.openbankmobiletest.domain.model.ApiError
import dev.dmanluc.openbankmobiletest.domain.model.PagingLoadTrackingState

sealed class CharactersViewState {
    object FirstLoading : CharactersViewState()
    object EmptyCharactersLoaded : CharactersViewState()
    data class CharactersLoaded(val pagingLoadTrackingState: PagingLoadTrackingState) :
        CharactersViewState()

    data class ErrorLoadingCharactersOnRefresh(val apiError: ApiError) : CharactersViewState()
    data class ErrorLoadingCharactersOnPaging(val apiError: ApiError, val fromOffset: Int) :
        CharactersViewState()

    object ErrorNoConnectivityOnRefresh : CharactersViewState()
    data class ErrorNoConnectivityOnPaging(val fromOffset: Int) : CharactersViewState()
}