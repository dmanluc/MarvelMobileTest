package dev.dmanluc.openbankmobiletest.presentation.characters

import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import dev.dmanluc.openbankmobiletest.databinding.ItemLoadingCharactersBinding

class LoadingCharactersViewHolder(private val binding: ItemLoadingCharactersBinding) :
    RecyclerView.ViewHolder(binding.root) {

    init {
        val layoutParams = itemView.layoutParams as StaggeredGridLayoutManager.LayoutParams
        layoutParams.isFullSpan = true
    }

}