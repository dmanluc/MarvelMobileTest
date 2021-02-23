package dev.dmanluc.openbankmobiletest.presentation.characterdetail

import android.os.Bundle
import dev.dmanluc.openbankmobiletest.R
import dev.dmanluc.openbankmobiletest.databinding.FragmentCharacterDetailBinding
import dev.dmanluc.openbankmobiletest.databinding.FragmentCharacterListBinding
import dev.dmanluc.openbankmobiletest.presentation.base.BaseFragment
import dev.dmanluc.openbankmobiletest.presentation.base.BaseViewModel
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

    override fun getViewModel(): BaseViewModel = viewModel

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        setupUi()
    }

    private fun setupUi() {

    }

}