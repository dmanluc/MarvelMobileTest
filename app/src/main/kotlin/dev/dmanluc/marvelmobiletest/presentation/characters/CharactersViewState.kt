package dev.dmanluc.marvelmobiletest.presentation.characters

import dev.dmanluc.marvelmobiletest.domain.model.ApiError
import dev.dmanluc.marvelmobiletest.domain.model.PagingLoadTrackingState

/**
 * @author   Daniel Manrique Lucas <dmanluc91@gmail.com>
 * @version  1
 *
 *  Definition of a hierachy of possible character list screen
 *
 */
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