package dev.dmanluc.marvelmobiletest.di

import dev.dmanluc.marvelmobiletest.data.local.AppDatabase
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