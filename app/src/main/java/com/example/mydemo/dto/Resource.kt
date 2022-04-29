package com.example.mydemo.dto


data class Resource<out T>(val status: Status, val res: T?, val message: String?, val code: Int) {
    companion object {
        fun <T> success(data: T, code: Int = 200): Resource<T> {
            return Resource(Status.SUCCESS, data, null, 200)
        }

        fun <T> error(msg: String?, data: T?, code: Int = -1): Resource<T> {
            return Resource(Status.ERROR, data, msg, code)
        }
    }

    fun isSuccess(): Boolean = status == Status.SUCCESS
    fun isError(): Boolean = status == Status.ERROR

    enum class Status {
        SUCCESS,
        ERROR
    }
}