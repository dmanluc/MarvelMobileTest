package dev.dmanluc.marvelmobiletest.presentation.characters

import android.view.View
import androidx.constraintlayout.widget.ConstraintSet
import androidx.recyclerview.widget.RecyclerView
import dev.dmanluc.marvelmobiletest.databinding.ItemCharacterListBinding
import dev.dmanluc.marvelmobiletest.domain.model.Character
import dev.dmanluc.marvelmobiletest.utils.hide
import dev.dmanluc.marvelmobiletest.utils.loadImage

class CharacterItemViewHolder(private val binding: ItemCharacterListBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bindCharacter(character: Character, onClickCharacterAction: ((View, Character) -> Unit)) {
        with(binding.characterCover) {
            transitionName = character.thumbnail
            loadImage(
                character.thumbnail,
                onResourceReadyDelegate = { hideLoadingCharacterCover() },
                onExceptionDelegate = { hideLoadingCharacterCover() },
                onSizeReady = { width, height ->
                    applyCoverDimensionRatio(width, height)
                })
        }

        binding.characterName.text = character.name

        binding.itemContainer.setOnClickListener {
            onClickCharacterAction(binding.characterCover, character)
        }
    }

    private fun hideLoadingCharacterCover() {
        binding.loadingCharacterCover.hide()
    }

    private fun applyCoverDimensionRatio(width: Int, height: Int) {
        ConstraintSet().apply {
            clone(binding.contentContainer)
            val ratio = "$width:$height"
            setDimensionRatio(binding.characterCover.id, ratio)
            applyTo(binding.contentContainer)
        }
    }

}