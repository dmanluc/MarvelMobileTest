package dev.dmanluc.marvelmobiletest.domain.model

sealed class PagingLoadTrackingState {
    data class Append(val characters: List<Character>) : PagingLoadTrackingState()
    data class Refresh(val characters: List<Character>) : PagingLoadTrackingState()
    object EndOfPagination : PagingLoadTrackingState()
}