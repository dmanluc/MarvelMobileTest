package dev.dmanluc.openbankmobiletest.presentation.characters

import android.os.Bundle
import android.view.View
import androidx.core.view.doOnPreDraw
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.transition.MaterialElevationScale
import dev.dmanluc.openbankmobiletest.R
import dev.dmanluc.openbankmobiletest.databinding.FragmentCharacterListBinding
import dev.dmanluc.openbankmobiletest.domain.model.Character
import dev.dmanluc.openbankmobiletest.presentation.base.BaseFragment
import dev.dmanluc.openbankmobiletest.presentation.base.BaseViewModel
import dev.dmanluc.openbankmobiletest.utils.EndlessRecyclerViewScrollListener
import dev.dmanluc.openbankmobiletest.utils.observeEvent
import dev.dmanluc.openbankmobiletest.utils.viewBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * @author   Daniel Manrique Lucas <dmanluc91@gmail.com>
 * @version  1
 *
 * First flow fragment which shows characters list from remote data source and user can navigate to its detail when clicking on it
 *
 */
class CharactersFragment : BaseFragment(R.layout.fragment_character_list) {

    private val viewModel: CharactersFragmentViewModel by viewModel()

    private val binding by viewBinding(FragmentCharacterListBinding::bind)

    private val charactersAdapter by lazy {
        CharactersPagingAdapter(onClickCharacterAction = ::onCharacterClicked)
    }

    private val endlessRecyclerViewScrollListener by lazy {
        EndlessRecyclerViewScrollListener { _: Int, totalItemsCount: Int, _: RecyclerView ->
            onScrollEndAction(totalItemsCount)
        }
    }

    private fun onCharacterClicked(view: View, selectedCharacter: Character) {
        exitTransition = MaterialElevationScale(false).apply {
            duration = resources.getInteger(R.integer.motion_duration_large).toLong()
        }

        reenterTransition = MaterialElevationScale(true).apply {
            duration = resources.getInteger(R.integer.motion_duration_large).toLong()
        }

        val extras = FragmentNavigatorExtras(view to selectedCharacter.thumbnail)
        findNavController().navigate(
            CharactersFragmentDirections.actionToCharacterDetail(
                selectedCharacter, selectedCharacter.name
            ), extras
        )
    }

    override fun getViewModel(): BaseViewModel = viewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        postponeEnterTransition()
        binding.characterRecyclerList.doOnPreDraw { startPostponedEnterTransition() }

        configureRecyclerView()
        setupUi()
    }

    private fun configureRecyclerView() {
        binding.characterRecyclerList.apply {
            layoutManager = GridLayoutManager(context, 2)
            adapter = charactersAdapter
            addOnScrollListener(endlessRecyclerViewScrollListener)
        }
    }

    private fun onScrollEndAction(totalItemCount: Int) {
        charactersAdapter.setPagingLoading()
        viewModel.loadCharacters(pagingOffset = totalItemCount)
    }

    private fun setupUi() {
        viewModel.pagingLoadTrackingStateLiveData.observeEvent(viewLifecycleOwner) { pagingState ->
            charactersAdapter.setAdapterItems(pagingState)
        }
    }

}