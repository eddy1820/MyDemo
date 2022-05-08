package com.example.mydemo.utils

import android.content.Context
import java.io.BufferedReader
import java.io.InputStreamReader

object AssetsUtil {
    fun getString(context: Context, filePath: String): String {
        val stream = context.assets.open(filePath)
        val byteArrays = stream.readBytes()
        stream.close()
        return String(byteArrays)
    }

    fun getStringList(context: Context, filePath: String): List<String> {
        var result: List<String>? = null
        var bufferReader: BufferedReader? = null
        try {
            bufferReader = BufferedReader(InputStreamReader(context.assets.open(filePath)))

            result = bufferReader.readLines()
        } catch (e: Exception) {
            bufferReader?.close()
        } finally {
            return result ?: emptyList()
        }
    }

    fun getBytes(context: Context, filePath: String): ByteArray? {
        val stream = context.assets.open(filePath)
        val byteArrays = stream.readBytes()
        stream.close()
        return byteArrays
    }
}