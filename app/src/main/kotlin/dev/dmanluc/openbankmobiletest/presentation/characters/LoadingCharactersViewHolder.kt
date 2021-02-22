package dev.dmanluc.openbankmobiletest.presentation.characters

import androidx.recyclerview.widget.RecyclerView
import dev.dmanluc.openbankmobiletest.R
import dev.dmanluc.openbankmobiletest.databinding.ItemCharacterListBinding
import dev.dmanluc.openbankmobiletest.databinding.ItemLoadingCharactersBinding
import dev.dmanluc.openbankmobiletest.domain.model.Character
import dev.dmanluc.openbankmobiletest.utils.loadImage

class LoadingCharactersViewHolder(private val binding: ItemLoadingCharactersBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(onRetryLoadingAction: () -> Unit = {}) {
    }

}