package dev.dmanluc.openbankmobiletest.domain.model

sealed class PagingLoadStatus(val pagingOffset: Int) {
    class Append(offset: Int) : PagingLoadStatus(offset)
    object Refresh : PagingLoadStatus(pagingOffset = 0)
}