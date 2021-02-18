package dev.dmanluc.openbankmobiletest.di

import dev.dmanluc.openbankmobiletest.domain.usecase.GetCharactersUseCase
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
}