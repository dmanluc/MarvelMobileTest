package dev.dmanluc.marvelmobiletest.presentation.characters

import dev.dmanluc.marvelmobiletest.domain.model.Character

/**
 * @author   Daniel Manrique Lucas <dmanluc91@gmail.com>
 * @version  1
 *
 *  Definition of an item to be displayed in characters list which contains character info content
 *
 */
class CharactersPagingAdapterContentItem(character: Character) :
    PagingAdapterItem<Character>(character) {

    override val itemType: PagingAdapterItemType
        get() = PagingAdapterItemType.CHARACTER

}