package dev.dmanluc.openbankmobiletest.presentation.characters

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import dev.dmanluc.openbankmobiletest.databinding.ItemCharacterListBinding
import dev.dmanluc.openbankmobiletest.domain.model.Character
import dev.dmanluc.openbankmobiletest.utils.loadImage

class CharacterItemViewHolder(private val binding: ItemCharacterListBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bindCharacter(character: Character, onClickCharacterAction: ((View, Character) -> Unit)) {
        binding.characterCover.transitionName = character.thumbnail
        binding.characterCover.loadImage(character.thumbnail)

        binding.itemContainer.setOnClickListener {
            onClickCharacterAction(binding.characterCover, character)
        }
    }

}