package dev.dmanluc.openbankmobiletest.di

import dev.dmanluc.openbankmobiletest.data.local.AppDatabase
import dev.dmanluc.openbankmobiletest.data.local.datasource.CharactersLocalDataSource
import dev.dmanluc.openbankmobiletest.data.local.datasource.CharactersLocalDataSourceImpl
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.Module
import org.koin.core.qualifier.named
import org.koin.dsl.module

/**
 * @author   Daniel Manrique Lucas <dmanluc91@gmail.com>
 * @version  1
 *
 * Koin DI module for local-storage based dependencies
 *
 */
private const val DATABASE = "DATABASE"

val localModule: Module = module {
    single(named(DATABASE)) { AppDatabase.buildDatabase(androidContext()) }
    factory { get<AppDatabase>(named(DATABASE)).charactersDao() }
}