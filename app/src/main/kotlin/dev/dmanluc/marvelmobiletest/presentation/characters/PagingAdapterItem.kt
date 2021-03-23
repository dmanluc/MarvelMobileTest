package dev.dmanluc.marvelmobiletest.presentation.characters

abstract class PagingAdapterItem<T>(var item: T? = null) {

    abstract val itemType: PagingAdapterItemType

}