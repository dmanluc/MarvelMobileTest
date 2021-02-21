package dev.dmanluc.openbankmobiletest.presentation.characters

import androidx.recyclerview.widget.RecyclerView
import dev.dmanluc.openbankmobiletest.R
import dev.dmanluc.openbankmobiletest.databinding.ItemCharacterListBinding
import dev.dmanluc.openbankmobiletest.domain.model.Character
import dev.dmanluc.openbankmobiletest.utils.loadImage

class CharacterItemViewHolder(private val binding: ItemCharacterListBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bindCharacter(character: Character, onClickCharacterAction: ((Character) -> Unit)) {
        binding.characterCover.loadImage(
            character.thumbnail,
            errorResource = R.drawable.ic_broken_image_black_24dp
        )

        itemView.setOnClickListener {
            onClickCharacterAction(character)
        }
    }

}