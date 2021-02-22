package dev.dmanluc.openbankmobiletest.domain.model

class PagingLoadTrackerImpl : PagingLoadTracker {

    private var pagingStatus: PagingLoadStatus = PagingLoadStatus.Refresh

    override fun getPagingStatus(): PagingLoadStatus {
        return pagingStatus
    }

    override fun appendFrom(pagingOffset: Int) {
        pagingStatus = PagingLoadStatus.Append(pagingOffset)
    }

    override fun reset() {
        pagingStatus = PagingLoadStatus.Refresh
    }

}

