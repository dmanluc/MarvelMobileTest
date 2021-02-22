package dev.dmanluc.openbankmobiletest.presentation.characters

abstract class PagingAdapterItem<T>(var item: T? = null) {

    abstract val itemType: PagingAdapterItemType

}