package dev.dmanluc.openbankmobiletest.data.datasource

sealed class ApiError {
    data class HttpError(val code: Int, val body: String) : ApiError()
    data class NetworkError(val throwable: Throwable) : ApiError()
    data class UnknownApiError(val throwable: Throwable) : ApiError()
}