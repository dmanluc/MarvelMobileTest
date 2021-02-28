package dev.dmanluc.openbankmobiletest.di

import dev.dmanluc.openbankmobiletest.domain.usecase.GetCharactersUseCase
import dev.dmanluc.openbankmobiletest.presentation.characters.CharactersFragmentViewModel
import dev.dmanluc.openbankmobiletest.utils.DefaultDispatcherProvider
import dev.dmanluc.openbankmobiletest.utils.DispatcherProvider
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

/**
 * @author   Daniel Manrique Lucas <dmanluc91@gmail.com>
 * @version  1
 *
 * Koin DI module for marvel characters feature dependencies
 *
 */
val charactersModule: Module = module {
    factory { GetCharactersUseCase(get()) }
    single { provideDispatcherProvider() }
    viewModel { CharactersFragmentViewModel(get(), get()) }
}

private fun provideDispatcherProvider(): DispatcherProvider = DefaultDispatcherProvider()
