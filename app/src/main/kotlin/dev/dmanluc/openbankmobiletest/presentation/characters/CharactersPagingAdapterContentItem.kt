package dev.dmanluc.openbankmobiletest.presentation.characters

import dev.dmanluc.openbankmobiletest.domain.model.Character

class CharactersPagingAdapterContentItem(character: Character) :
    PagingAdapterItem<Character>(character) {

    override val itemType: PagingAdapterItemType
        get() = PagingAdapterItemType.CHARACTER

}