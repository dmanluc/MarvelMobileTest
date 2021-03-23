package dev.dmanluc.marvelmobiletest.core

import android.app.Application
import dev.dmanluc.marvelmobiletest.BuildConfig
import dev.dmanluc.marvelmobiletest.di.*
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

/**
 * @author   Daniel Manrique Lucas <dmanluc91@gmail.com>
 * @version  1
 */
class App : Application() {

    override fun onCreate() {
        super.onCreate()
        setupKoinDi()
    }

    private fun setupKoinDi() {
        startKoin {
            androidLogger()
            androidContext(this@App)
            koin.loadModules(
                listOf(
                    localModule,
                    createNetworkModule(BuildConfig.MARVEL_BASE_URL),
                    repositoryModule,
                    charactersModule,
                )
            )
            koin.createRootScope()
        }
    }

}