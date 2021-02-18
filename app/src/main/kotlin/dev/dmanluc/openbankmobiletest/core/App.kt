package dev.dmanluc.openbankmobiletest.core

import android.app.Application
import dev.dmanluc.openbankmobiletest.di.networkModule
import dev.dmanluc.openbankmobiletest.di.repositoryModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

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
            androidLogger(Level.DEBUG)
            androidContext(this@App)
            modules(
                listOf(
                    networkModule,
                    repositoryModule
                )
            )
        }
    }

}