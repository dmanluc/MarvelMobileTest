package dev.dmanluc.openbankmobiletest.di

import com.google.gson.Gson
import dev.dmanluc.openbankmobiletest.data.api.MarvelApi
import dev.dmanluc.openbankmobiletest.data.datasource.ApiManager
import dev.dmanluc.openbankmobiletest.data.datasource.CharactersRemoteDataSource
import dev.dmanluc.openbankmobiletest.data.datasource.CharactersRemoteDataSourceImpl
import dev.dmanluc.openbankmobiletest.data.repository.MarvelCharactersRepositoryImpl
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
    single { provideApiManager(get()) }
    single { provideRemoteDataSource(get(), get()) }
    single { provideCharactersRepository(get()) }
}

private fun provideApiManager(gson: Gson): ApiManager = ApiManager(gson)

private fun provideRemoteDataSource(
    marvelApi: MarvelApi,
    apiManager: ApiManager
): CharactersRemoteDataSource = CharactersRemoteDataSourceImpl(marvelApi, apiManager)

private fun provideCharactersRepository(remoteDataSource: CharactersRemoteDataSource): MarvelCharactersRepository =
    MarvelCharactersRepositoryImpl(remoteDataSource)