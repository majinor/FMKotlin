package com.daffamuhtar.fmkotlin.util

data class OldResource<out T>(
    val status: Status,
    val data: T?,
    val message: String?,
    val responseCode: Int
) {

    companion object {

        fun <T> success(data: T?, responseCode: Int): OldResource<T> {
            return OldResource(Status.SUCCESS, data, null, responseCode)
        }

        fun <T> error(msg: String, data: T?, responseCode: Int): OldResource<T> {
            return OldResource(Status.ERROR, data, msg, responseCode)
        }

        fun <T> loading(data: T?): OldResource<T> {
            return OldResource(Status.LOADING, data, null, 0)
        }

    }

}