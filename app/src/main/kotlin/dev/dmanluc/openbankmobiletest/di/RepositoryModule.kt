package dev.dmanluc.openbankmobiletest.di

import dev.dmanluc.openbankmobiletest.data.remote.api.MarvelApi
import dev.dmanluc.openbankmobiletest.data.remote.datasource.ApiManager
import dev.dmanluc.openbankmobiletest.data.remote.datasource.CharactersRemoteDataSource
import dev.dmanluc.openbankmobiletest.data.remote.datasource.CharactersRemoteDataSourceImpl
import dev.dmanluc.openbankmobiletest.data.remote.repository.MarvelCharactersRepositoryImpl
import dev.dmanluc.openbankmobiletest.domain.repository.MarvelCharactersRepository
import org.koin.core.module.Module
import org.koin.dsl.module

/**
 * @author   Daniel Manrique Lucas <dmanluc91@gmail.com>
 * @version  1
 *
 * Koin DI module for repository dependencies
 *
 */
val repositoryModule: Module = module {
    single { provideRemoteDataSource(get(), get()) }
    single { provideCharactersRepository(get()) }
}

private fun provideRemoteDataSource(
    marvelApi: MarvelApi,
    apiManager: ApiManager
): CharactersRemoteDataSource = CharactersRemoteDataSourceImpl(marvelApi, apiManager)

private fun provideCharactersRepository(remoteDataSource: CharactersRemoteDataSource): MarvelCharactersRepository =
    MarvelCharactersRepositoryImpl(remoteDataSource)