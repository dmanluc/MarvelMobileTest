package dev.dmanluc.openbankmobiletest.presentation.characters

import android.os.Bundle
import android.view.View
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager.GAP_HANDLING_NONE
import androidx.recyclerview.widget.StaggeredGridLayoutManager.VERTICAL
import androidx.test.espresso.idling.CountingIdlingResource
import com.google.android.material.transition.MaterialElevationScale
import dev.dmanluc.openbankmobiletest.R
import dev.dmanluc.openbankmobiletest.databinding.FragmentCharacterListBinding
import dev.dmanluc.openbankmobiletest.domain.model.Character
import dev.dmanluc.openbankmobiletest.presentation.custom.ErrorDataItem
import dev.dmanluc.openbankmobiletest.utils.*
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * @author   Daniel Manrique Lucas <dmanluc91@gmail.com>
 * @version  1
 *
 * First flow fragment which shows characters list from remote data source and user can navigate to its detail when clicking on it
 *
 */
class CharactersFragment : Fragment(R.layout.fragment_character_list) {

    private val viewModel: CharactersFragmentViewModel by viewModel()

    private val binding by viewBinding(FragmentCharacterListBinding::bind)

    private val charactersAdapter by lazy {
        CharactersPagingAdapter(onClickCharacterAction = ::onCharacterClicked)
    }

    private val endlessRecyclerViewScrollListener by lazy {
        EndlessRecyclerViewScrollListener { itemCount ->
            onScrollEndAction(itemCount)
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

    val countingIdlingResource = CountingIdlingResource("CharactersFragment")

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        postponeEnterTransition()
        binding.characterRecyclerList.doOnPreDraw { startPostponedEnterTransition() }

        configureRecyclerView()
        setupUi()
    }

    private fun configureRecyclerView() {
        binding.characterRecyclerList.apply {
            layoutManager = StaggeredGridLayoutManager(2, VERTICAL).apply {
                gapStrategy = GAP_HANDLING_NONE
            }
            adapter = charactersAdapter
            addOnScrollListener(endlessRecyclerViewScrollListener)
        }
    }

    private fun onScrollEndAction(totalItemCount: Int) {
        countingIdlingResource.increment()
        performCharactersPagingLoad(totalItemCount)
    }

    private fun performCharactersPagingLoad(totalItemCount: Int) {
        charactersAdapter.setPagingLoading()
        viewModel.loadCharacters(pagingOffset = totalItemCount)
    }

    private fun setupUi() {
        viewModel.charactersViewSateLiveData.observeEvent(viewLifecycleOwner) { viewState ->
            handleViewState(viewState)
            if (countingIdlingResource.isIdleNow.not()) countingIdlingResource.decrement()
        }
        binding.charactersSwipeRefreshLayout.setOnRefreshListener {
            viewModel.refreshCharacters()
        }

    }

    private fun handleViewState(state: CharactersViewState) {
        when (state) {
            is CharactersViewState.CharactersLoaded -> {
                hideRefreshLoadingIndicators()
                endlessRecyclerViewScrollListener.setLoaded()
                charactersAdapter.setAdapterItems(state.pagingLoadTrackingState)
            }
            CharactersViewState.EmptyCharactersLoaded -> {
                hideRefreshLoadingIndicators()
                val isPreviousDataShown = charactersAdapter.itemCount != 0
                handleEmptyView(isPreviousDataShown)
            }
            is CharactersViewState.ErrorLoadingCharactersOnRefresh -> {
                hideRefreshLoadingIndicators()
                val isPreviousDataShown = charactersAdapter.itemCount != 0
                handleLoadingErrorView(isPreviousDataShown)
            }
            is CharactersViewState.ErrorLoadingCharactersOnPaging -> {
                hidePagingLoadingIndicators()
                showSnackbar(R.string.error_fetching_characters_text,
                    R.string.retry_loading_action_text,
                    snackbarActionListener = {
                        performCharactersPagingLoad(state.fromOffset)
                    })
            }
            is CharactersViewState.ErrorNoConnectivityOnRefresh -> {
                hideRefreshLoadingIndicators()
                val isPreviousDataShown = charactersAdapter.itemCount != 0
                handleConnectivityErrorView(isPreviousDataShown)

            }
            is CharactersViewState.ErrorNoConnectivityOnPaging -> {
                hidePagingLoadingIndicators()
                showSnackbar(R.string.no_internet_connection_text,
                    R.string.retry_loading_action_text,
                    snackbarActionListener = {
                        performCharactersPagingLoad(state.fromOffset)
                    })
            }
            CharactersViewState.FirstLoading -> {
                binding.lottieLoadingCharacters.show()
            }
        }
    }

    private fun hidePagingLoadingIndicators() {
        charactersAdapter.removePagingLoading()
        endlessRecyclerViewScrollListener.setLoaded()
    }

    private fun hideRefreshLoadingIndicators() {
        binding.lottieLoadingCharacters.hide()
        binding.errorView.hide()
        binding.charactersSwipeRefreshLayout.isRefreshing = false
    }

    private fun performRefreshCharactersLoad() {
        binding.charactersSwipeRefreshLayout.isRefreshing = true
        viewModel.refreshCharacters()
    }

    private fun handleEmptyView(isPreviousDataShown: Boolean) {
        if (!isPreviousDataShown) {
            val errorData = ErrorDataItem(
                R.drawable.ic_empty_result,
                R.string.empty_characters_view_text,
                onErrorAction = ::performRefreshCharactersLoad
            )
            binding.errorView.setData(errorData)
            binding.errorView.show()
        }
    }

    private fun handleLoadingErrorView(isPreviousDataShown: Boolean) {
        if (isPreviousDataShown) {
            binding.errorView.hide()
            showSnackbar(R.string.error_fetching_characters_text,
                R.string.retry_loading_action_text,
                snackbarActionListener = {
                    performRefreshCharactersLoad()
                })
        } else {
            val errorData = ErrorDataItem(
                R.drawable.ic_empty_result,
                R.string.error_fetching_characters_text,
                onErrorAction = ::performRefreshCharactersLoad
            )
            binding.errorView.setData(errorData)
            binding.errorView.show()
        }
    }

    private fun handleConnectivityErrorView(isPreviousDataShown: Boolean) {
        if (isPreviousDataShown) {
            binding.errorView.hide()
            showSnackbar(R.string.no_internet_connection_text,
                R.string.retry_loading_action_text,
                snackbarActionListener = {
                    performRefreshCharactersLoad()
                })
        } else {
            val errorData = ErrorDataItem(
                R.drawable.ic_no_connectivity,
                R.string.no_internet_connection_text,
                onErrorAction = ::performRefreshCharactersLoad
            )
            binding.errorView.setData(errorData)
            binding.errorView.show()
        }
    }

}