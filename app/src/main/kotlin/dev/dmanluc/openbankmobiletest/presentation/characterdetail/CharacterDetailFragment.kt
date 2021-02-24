package dev.dmanluc.openbankmobiletest.presentation.characterdetail

import android.graphics.Color
import android.os.Bundle
import androidx.navigation.fragment.navArgs
import com.google.android.material.transition.MaterialContainerTransform
import dev.dmanluc.openbankmobiletest.R
import dev.dmanluc.openbankmobiletest.databinding.FragmentCharacterDetailBinding
import dev.dmanluc.openbankmobiletest.domain.model.Character
import dev.dmanluc.openbankmobiletest.presentation.MarvelActivity
import dev.dmanluc.openbankmobiletest.presentation.base.BaseFragment
import dev.dmanluc.openbankmobiletest.presentation.base.BaseViewModel
import dev.dmanluc.openbankmobiletest.utils.loadImage
import dev.dmanluc.openbankmobiletest.utils.viewBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * @author   Daniel Manrique Lucas <dmanluc91@gmail.com>
 * @version  1
 *
 * Second screen of the app flow which shows selected character's details
 *
 */
class CharacterDetailFragment : BaseFragment(R.layout.fragment_character_detail) {

    private val viewModel: CharacterDetailFragmentViewModel by viewModel()

    private val binding by viewBinding(FragmentCharacterDetailBinding::bind)

    private val args: CharacterDetailFragmentArgs by navArgs()

    override fun getViewModel(): BaseViewModel = viewModel

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        sharedElementEnterTransition = MaterialContainerTransform().apply {
            drawingViewId = R.id.navHostFragment
            duration = resources.getInteger(R.integer.motion_duration_large).toLong()
            scrimColor = Color.TRANSPARENT
        }
        postponeEnterTransition()

        setupUi()
    }

    private fun setupUi() {
        val characterSelected = args.character

        showCharacterInfo(characterSelected)
    }

    private fun showCharacterInfo(character: Character) {
        with(binding) {
            characterDetailImage.transitionName = character.thumbnail
            characterDetailImage.loadImage(character.thumbnail,
                onResourceReadyDelegate = { startPostponedEnterTransition() },
                onExceptionDelegate = { startPostponedEnterTransition() })

            characterDetailDescription.text = character.description
            characterDetailComicsNumber.text = character.comicsSummary.available.toString()
            characterDetailSeriesNumber.text = character.seriesSummary.available.toString()
            characterDetailEventsNumber.text = character.eventsSummary.available.toString()
            characterDetailStoriesNumber.text = character.storiesSummary.available.toString()
        }
    }

}