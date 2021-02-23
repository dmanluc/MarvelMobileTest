package dev.dmanluc.openbankmobiletest.presentation.characters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import dev.dmanluc.openbankmobiletest.databinding.ItemCharacterListBinding
import dev.dmanluc.openbankmobiletest.databinding.ItemLoadingCharactersBinding
import dev.dmanluc.openbankmobiletest.domain.model.Character
import dev.dmanluc.openbankmobiletest.domain.model.PagingLoadStatus
import dev.dmanluc.openbankmobiletest.domain.model.PagingLoadTracker

/**
 * @author   Daniel Manrique Lucas <dmanluc91@gmail.com>
 * @version  1
 *
 * Adapter which populates market product list with market product items
 *
 */
class CharactersPagingAdapter(
    private val onClickCharacterAction: ((View, Character) -> Unit)
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val items: MutableList<PagingAdapterItem<*>> = mutableListOf()
    private var isLoading: Boolean = false

    private val characterComparator = { oldCharacter: Character, newCharacter: Character ->
        oldCharacter.id == newCharacter.id
                && oldCharacter.name == newCharacter.name
                && oldCharacter.modifiedDate == newCharacter.modifiedDate
                && oldCharacter.description == newCharacter.description
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            PagingAdapterItemType.LOADING.typeCode -> {
                val binding = ItemLoadingCharactersBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                LoadingCharactersViewHolder(binding)
            }
            else -> {
                val binding = ItemCharacterListBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                CharacterItemViewHolder(binding)
            }
        }

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is LoadingCharactersViewHolder -> {
                // TODO
            }
            is CharacterItemViewHolder -> {
                (items[position].item as? Character)?.let {
                    holder.bindCharacter(
                        it,
                        onClickCharacterAction
                    )
                }
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return items[position].itemType.typeCode
    }

    override fun getItemCount() = items.size

    fun setPagingLoading() {
        if (!isLoading) {
            isLoading = true
            items.add(CharactersPagingAdapterLoadingItem())
            notifyItemInserted(items.size - 1)
        }
    }

    fun setAdapterItems(characters: List<Character>, pagingLoadTracker: PagingLoadTracker) {
        val oldItems = items.toList()

        when (pagingLoadTracker.getPagingStatus()) {
            is PagingLoadStatus.Append -> {
                items.addAll(characters.map { CharactersPagingAdapterContentItem(it) })
            }
            PagingLoadStatus.Refresh -> {
                items.apply {
                    clear()
                    addAll(characters.map { CharactersPagingAdapterContentItem(it) })
                }
            }
        }

        val diffCallback = PagingAdapterDiffUtils(oldItems, items, characterComparator)
        val diffResult = DiffUtil.calculateDiff(diffCallback)

        diffResult.dispatchUpdatesTo(this)

        if (pagingLoadTracker.getPagingStatus().pagingOffset > 0) {
            removePagingLoading(oldItems.size - 1)
        }
    }

    private fun removePagingLoading(position: Int) {
        isLoading = false
        val checkedPosition = position.coerceAtLeast(0)
        items.removeAt(checkedPosition)
        notifyItemRemoved(checkedPosition)
    }

}