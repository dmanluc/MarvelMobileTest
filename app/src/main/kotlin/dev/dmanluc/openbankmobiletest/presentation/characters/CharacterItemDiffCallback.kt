package dev.dmanluc.openbankmobiletest.presentation.characters

import androidx.recyclerview.widget.DiffUtil
import dev.dmanluc.openbankmobiletest.domain.model.Character

class CharacterItemDiffCallback(
    private val oldList: List<Character>,
    private val newList: List<Character>
) : DiffUtil.Callback() {

    override fun getOldListSize() = oldList.size

    override fun getNewListSize() = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) =
        oldList[oldItemPosition] == newList[newItemPosition]

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].id == newList[newItemPosition].id
                && oldList[oldItemPosition].name == newList[newItemPosition].name
                && oldList[oldItemPosition].modifiedDate == newList[newItemPosition].modifiedDate
                && oldList[oldItemPosition].description == newList[newItemPosition].description
    }
}