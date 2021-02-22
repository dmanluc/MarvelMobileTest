package dev.dmanluc.openbankmobiletest.presentation.characters

class CharactersPagingAdapterLoadingItem :
    PagingAdapterItem<Any>() {

    override val itemType: PagingAdapterItemType
        get() = PagingAdapterItemType.LOADING

}