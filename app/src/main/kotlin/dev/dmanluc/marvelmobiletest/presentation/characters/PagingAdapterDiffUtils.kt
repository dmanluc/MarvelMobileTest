package dev.dmanluc.marvelmobiletest.presentation.characters

import androidx.recyclerview.widget.DiffUtil

@Suppress("UNCHECKED_CAST")
class PagingAdapterDiffUtils<ITEM>(
    private val oldList: List<PagingAdapterItem<*>>,
    private val newList: List<PagingAdapterItem<*>>,
    private val comparator: ((ITEM, ITEM) -> Boolean)?,
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldList[oldItemPosition].item as? ITEM ?: return false
        val newItem = newList[newItemPosition].item as? ITEM ?: return false
        return comparator?.invoke(oldItem, newItem) ?: false
    }


    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldList[oldItemPosition].item as? ITEM ?: return false
        val newItem = newList[newItemPosition].item as? ITEM ?: return false
        return comparator?.invoke(oldItem, newItem) ?: false
    }
}