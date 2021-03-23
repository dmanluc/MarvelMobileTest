package dev.dmanluc.marvelmobiletest.di

import dev.dmanluc.marvelmobiletest.domain.usecase.GetCharactersUseCase
import dev.dmanluc.marvelmobiletest.presentation.characters.CharactersFragmentViewModel
import dev.dmanluc.marvelmobiletest.utils.DefaultDispatcherProvider
import dev.dmanluc.marvelmobiletest.utils.DispatcherProvider
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
