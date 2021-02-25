package dev.dmanluc.openbankmobiletest.di

import com.google.gson.Gson
import dev.dmanluc.openbankmobiletest.data.local.dao.CharactersDao
import dev.dmanluc.openbankmobiletest.data.local.datasource.CharactersLocalDataSource
import dev.dmanluc.openbankmobiletest.data.local.datasource.CharactersLocalDataSourceImpl
import dev.dmanluc.openbankmobiletest.data.remote.api.MarvelApi
import dev.dmanluc.openbankmobiletest.data.remote.datasource.CharactersRemoteDataSource
import dev.dmanluc.openbankmobiletest.data.remote.datasource.CharactersRemoteDataSourceImpl
import dev.dmanluc.openbankmobiletest.data.repository.CharactersDataSourceManager
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
    factory { CharactersDataSourceManager(get()) }
    factory { provideLocalDataSource(get()) }
    factory { provideRemoteDataSource(get(), get()) }
    factory { provideCharactersRepository(get(), get(), get()) }
}

private fun provideLocalDataSource(
    charactersDao: CharactersDao
): CharactersLocalDataSource = CharactersLocalDataSourceImpl(charactersDao)

private fun provideRemoteDataSource(
    marvelApi: MarvelApi,
    gson: Gson
): CharactersRemoteDataSource = CharactersRemoteDataSourceImpl(marvelApi, gson)

private fun provideCharactersRepository(
    localDataSource: CharactersLocalDataSource,
    remoteDataSource: CharactersRemoteDataSource,
    charactersDataSourceManager: CharactersDataSourceManager
): MarvelCharactersRepository =
    MarvelCharactersRepositoryImpl(localDataSource, remoteDataSource, charactersDataSourceManager)