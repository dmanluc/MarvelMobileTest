package dev.dmanluc.openbankmobiletest.presentation.characters

import android.view.View
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.RecyclerView
import dev.dmanluc.openbankmobiletest.R
import dev.dmanluc.openbankmobiletest.databinding.ItemCharacterListBinding
import dev.dmanluc.openbankmobiletest.domain.model.Character
import dev.dmanluc.openbankmobiletest.utils.loadImage

class CharacterItemViewHolder(private val binding: ItemCharacterListBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bindCharacter(character: Character, onClickCharacterAction: ((View, Character) -> Unit)) {
        ViewCompat.setTransitionName(binding.itemContainer, character.thumbnail)
        binding.characterCover.loadImage(character.thumbnail)

        itemView.setOnClickListener {
            onClickCharacterAction(it, character)
        }
    }

}