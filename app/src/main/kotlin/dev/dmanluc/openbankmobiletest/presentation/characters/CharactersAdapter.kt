package dev.dmanluc.openbankmobiletest.presentation.characters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import dev.dmanluc.openbankmobiletest.databinding.ItemCharacterListBinding
import dev.dmanluc.openbankmobiletest.domain.model.Character

/**
 * @author   Daniel Manrique Lucas <dmanluc91@gmail.com>
 * @version  1
 *
 * Adapter which populates market product list with market product items
 *
 */
class CharactersAdapter(
    private val onClickCharacterAction: ((Character) -> Unit)
) : RecyclerView.Adapter<CharacterItemViewHolder>() {

    private val items: MutableList<Character> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterItemViewHolder {
        val binding =
            ItemCharacterListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CharacterItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CharacterItemViewHolder, position: Int) {
        holder.bindCharacter(items[position], onClickCharacterAction)
    }

    override fun getItemCount() = items.size

    fun setAdapterItems(products: List<Character>) {
        val diffCallback = CharacterItemDiffCallback(items, products)
        val diffResult = DiffUtil.calculateDiff(diffCallback)

        items.apply {
            clear()
            addAll(products)
        }

        diffResult.dispatchUpdatesTo(this)
    }

}