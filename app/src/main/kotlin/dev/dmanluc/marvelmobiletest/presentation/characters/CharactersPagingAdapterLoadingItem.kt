package dev.dmanluc.marvelmobiletest.presentation.characters

/**
 * @author   Daniel Manrique Lucas <dmanluc91@gmail.com>
 * @version  1
 *
 *  Definition of an item to be displayed in characters list when performing pagination to indicate fetching data loading progress
 *
 */
class CharactersPagingAdapterLoadingItem :
    PagingAdapterItem<Any>() {

    override val itemType: PagingAdapterItemType
        get() = PagingAdapterItemType.LOADING

}