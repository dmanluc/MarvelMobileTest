package dev.dmanluc.openbankmobiletest.presentation.characterdetail

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.google.android.material.transition.MaterialContainerTransform
import dev.dmanluc.openbankmobiletest.R
import dev.dmanluc.openbankmobiletest.databinding.FragmentCharacterDetailBinding
import dev.dmanluc.openbankmobiletest.domain.model.Character
import dev.dmanluc.openbankmobiletest.domain.model.CharacterUrlType
import dev.dmanluc.openbankmobiletest.domain.model.UrlItem
import dev.dmanluc.openbankmobiletest.presentation.custom.ChipDetailInfoDataItem
import dev.dmanluc.openbankmobiletest.utils.*

/**
 * @author   Daniel Manrique Lucas <dmanluc91@gmail.com>
 * @version  1
 *
 * Second screen of the app flow which shows selected character's details
 *
 */
class CharacterDetailFragment : Fragment(R.layout.fragment_character_detail) {

    private val binding by viewBinding(FragmentCharacterDetailBinding::bind)

    private val args: CharacterDetailFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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
                onResourceReadyDelegate = { applyAfterLoadingCharacterImageAction() },
                onExceptionDelegate = { applyAfterLoadingCharacterImageAction() })

            characterDetailDescription.textOrHide(character.description)

            val comicsData = ChipDetailInfoDataItem(
                R.string.character_detail_featured_comics_text,
                character.comicsSummary.available.toString()
            )
            val seriesData = ChipDetailInfoDataItem(
                R.string.character_detail_featured_series_text,
                character.seriesSummary.available.toString()
            )
            val eventsData = ChipDetailInfoDataItem(
                R.string.character_detail_featured_events_text,
                character.eventsSummary.available.toString()
            )
            val storiesData = ChipDetailInfoDataItem(
                R.string.character_detail_featured_stories_text,
                character.storiesSummary.available.toString()
            )

            characterDetailComics.setData(comicsData)
            characterDetailSeries.setData(seriesData)
            characterDetailEvents.setData(eventsData)
            characterDetailStories.setData(storiesData)

            character.urls?.let(::setupCharacterLinkResourcesNavigation)
                ?: characterDetailAdditionalInfoTitle.hide()
        }
    }

    private fun applyAfterLoadingCharacterImageAction() {
        binding.loadingCharacterImage.hide()
        startPostponedEnterTransition()
    }

    private fun setupCharacterLinkResourcesNavigation(urlItems: List<UrlItem>) {
        urlItems.forEach { urlItem ->
            when (urlItem.type) {
                CharacterUrlType.DETAIL -> {
                    with(binding.characterDetailAdditionalInfoDetailRes) {
                        show()
                        setOnClickListener { activity?.let { urlItem.url.navigateToUrl(it) } }
                    }
                }
                CharacterUrlType.COMIC -> {
                    with(binding.characterDetailAdditionalInfoComicsRes) {
                        show()
                        setOnClickListener { activity?.let { urlItem.url.navigateToUrl(it) } }
                    }
                }
                CharacterUrlType.WIKI -> {
                    with(binding.characterDetailAdditionalInfoWikiRes) {
                        show()
                        setOnClickListener { activity?.let { urlItem.url.navigateToUrl(it) } }
                    }
                }
                else -> return
            }
        }
    }

}