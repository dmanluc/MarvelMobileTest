package dev.dmanluc.openbankmobiletest.domain.model

sealed class PagingLoadTrackingState {
    class Append(val characters: List<Character>) : PagingLoadTrackingState()
    class Refresh(val characters: List<Character>) : PagingLoadTrackingState()
    object EndOfPagination : PagingLoadTrackingState()
}