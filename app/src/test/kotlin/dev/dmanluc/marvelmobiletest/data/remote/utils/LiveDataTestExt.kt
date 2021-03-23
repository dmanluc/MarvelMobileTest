package dev.dmanluc.marvelmobiletest.data.remote.utils

import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer

/**
 * @author   Daniel Manrique Lucas <dmanluc91@gmail.com>
 * @version  1
 */

/**
 * Observes a [LiveData] until the `block` is done executing.
 */
fun <T> LiveData<T>.observeForTesting(observer: Observer<T> = Observer {}, block: () -> Unit) {
    try {
        observeForever(observer)
        block()
    } finally {
        removeObserver(observer)
    }
}