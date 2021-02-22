package dev.dmanluc.openbankmobiletest.domain.model

interface PagingLoadTracker {

    fun getPagingStatus(): PagingLoadStatus

    fun appendFrom(pagingOffset: Int)

    fun reset()

}