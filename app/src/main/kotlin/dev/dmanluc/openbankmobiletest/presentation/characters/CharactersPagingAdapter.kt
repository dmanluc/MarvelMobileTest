package dev.dmanluc.openbankmobiletest.presentation.characters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import dev.dmanluc.openbankmobiletest.databinding.ItemCharacterListBinding
import dev.dmanluc.openbankmobiletest.databinding.ItemLoadingCharactersBinding
import dev.dmanluc.openbankmobiletest.domain.model.Character
import dev.dmanluc.openbankmobiletest.domain.model.PagingLoadTrackingState

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

    fun setAdapterItems(state: PagingLoadTrackingState) {
        val oldItems = items.toList()

        when (state) {
            is PagingLoadTrackingState.Append -> {
                appendCharacters(state.characters)
            }
            is PagingLoadTrackingState.Refresh -> {
                refreshCharacters(state.characters)
            }
            is PagingLoadTrackingState.EndOfPagination -> {
                removePagingLoading(oldItems.size - 1)
                return
            }
        }
    }

    private fun appendCharacters(characters: List<Character>) {
        val oldItems = items.toList()

        items.addAll(characters.map { CharactersPagingAdapterContentItem(it) })

        val diffCallback = PagingAdapterDiffUtils(oldItems, items, characterComparator)
        val diffResult = DiffUtil.calculateDiff(diffCallback)

        diffResult.dispatchUpdatesTo(this)

        removePagingLoading(oldItems.size - 1)
    }

    private fun refreshCharacters(characters: List<Character>) {
        val oldItems = items.toList()

        items.apply {
            clear()
            addAll(characters.map { CharactersPagingAdapterContentItem(it) })
        }

        val diffCallback = PagingAdapterDiffUtils(oldItems, items, characterComparator)
        val diffResult = DiffUtil.calculateDiff(diffCallback)

        diffResult.dispatchUpdatesTo(this)
    }

    fun removePagingLoading(position: Int? = null) {
        if (itemCount == 0) return

        isLoading = false
        val checkedPosition = position ?: itemCount - 1
        items.removeAt(checkedPosition)
        notifyItemRemoved(checkedPosition)
    }

}