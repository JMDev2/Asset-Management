package com.ekenya.rnd.common.utils

data class Resource<out T>(val status: Status, val data: T?, val message: String?, val responseCode: Int? = null) {
    companion object {
        fun <T> success(data: T, responseCode: Int? = null): Resource<T> {
            return Resource(Status.SUCCESS, data, null, responseCode)
        }

        fun <T> error(msg: String?, data: T, responseCode: Int? = null): Resource<T> {
            return Resource(Status.ERROR, data, msg, responseCode)
        }

        fun <T> loading(data: T?): Resource<T> {
            return Resource(Status.LOADING, data, null)
        }
    }
}
