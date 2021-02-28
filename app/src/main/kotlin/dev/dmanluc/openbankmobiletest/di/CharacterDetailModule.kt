package dev.dmanluc.openbankmobiletest.di

import dev.dmanluc.openbankmobiletest.presentation.characterdetail.CharacterDetailFragmentViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

/**
 * @author   Daniel Manrique Lucas <dmanluc91@gmail.com>
 * @version  1
 *
 * Koin DI module for marvel character detail feature dependencies
 *
 */
val characterDetailModule: Module = module {
    viewModel { CharacterDetailFragmentViewModel() }
}