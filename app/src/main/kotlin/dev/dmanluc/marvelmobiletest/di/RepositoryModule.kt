package dev.dmanluc.marvelmobiletest.di

import com.google.gson.Gson
import dev.dmanluc.marvelmobiletest.data.local.dao.CharactersDao
import dev.dmanluc.marvelmobiletest.data.local.datasource.CharactersLocalDataSource
import dev.dmanluc.marvelmobiletest.data.local.datasource.CharactersLocalDataSourceImpl
import dev.dmanluc.marvelmobiletest.data.remote.api.MarvelApi
import dev.dmanluc.marvelmobiletest.data.remote.datasource.CharactersRemoteDataSource
import dev.dmanluc.marvelmobiletest.data.remote.datasource.CharactersRemoteDataSourceImpl
import dev.dmanluc.marvelmobiletest.data.repository.CharactersRepositoryImpl
import dev.dmanluc.marvelmobiletest.data.repository.DataSourceManager
import dev.dmanluc.marvelmobiletest.domain.repository.CharactersRepository
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
    factory { DataSourceManager(get()) }
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
    dataSourceManager: DataSourceManager
): CharactersRepository =
    CharactersRepositoryImpl(localDataSource, remoteDataSource, dataSourceManager)