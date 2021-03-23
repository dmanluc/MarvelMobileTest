package dev.dmanluc.marvelmobiletest.domain.model

/**
 * @author   Daniel Manrique Lucas <dmanluc91@gmail.com>
 * @version  1
 *
 *  Definition of a hierachy of possible errors happening when fetching from API
 *
 */
sealed class ApiError {
    data class HttpError(val code: Int, val body: String) : ApiError()
    data class NetworkError(val throwable: Throwable) : ApiError()
    data class UnknownApiError(val throwable: Throwable) : ApiError()
}