package com.example.mydemo.dto


data class Resource<out T>(val status: Status, val res: T?, val message: String?) {
  companion object {
    fun <T> success(data: T): Resource<T> {
      return Resource(Status.SUCCESS, data, null)
    }

    fun <T> error(msg: String?, data: T?): Resource<T> {
      return Resource(Status.ERROR, data, msg)
    }
  }

  fun isSuccess(): Boolean = status == Status.SUCCESS
  fun isError(): Boolean = status == Status.ERROR

  enum class Status {
    SUCCESS,
    ERROR
  }
}