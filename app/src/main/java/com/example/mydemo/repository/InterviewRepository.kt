package com.example.mydemo.repository

import com.example.mydemo.dto.Resource
import com.example.mydemo.service.InterviewService
import com.example.mydemo.extension.ioToUi
import com.example.mydemo.model.VectorResponse
import com.example.mydemo.model.VectorResponseFail
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class InterviewRepository(private val interviewService: InterviewService) {
    suspend fun getVector() = interviewService.getVectorWithFlow()

    //successful
    suspend fun getVectorWithFlow(): Flow<Resource<VectorResponse>> {
        return flow {
            val data = interviewService.getVectorWithFlow()
            if (data.isSuccessful) {
                emit(Resource.success(data.body() as VectorResponse))
            } else {
                emit(Resource.error(data.message(), null, data.code()))
            }
        }

    }

    //successful
    suspend fun getVectorWithFlowTwo(): Flow<Resource<VectorResponse>> {
        return flow {
            val data = interviewService.getVectorWithFlow()
            delay(3000)
            if (data.isSuccessful) {
                emit(Resource.success(data.body() as VectorResponse))
            } else {
                emit(Resource.error(data.message(), null, data.code()))
            }
        }

    }

    //Refresh Token
    suspend fun getVectorWithFlow2(): Flow<Resource<VectorResponse>> {
        return flow {
            val data = interviewService.getVectorWithFlow2()
            if (data.isSuccessful) {
                emit(Resource.success(data.body() as VectorResponse))
            } else {
                emit(Resource.error(data.message(), null, data.code()))
            }
        }

    }

    //data is corrupt
    suspend fun getVectorWithFlow3(): Flow<Resource<VectorResponseFail>> {
        return flow {
            val data = interviewService.getVectorWithFlow3()
            if (data.isSuccessful) {
                emit(Resource.success(data.body() as VectorResponseFail))
            } else {
                emit(Resource.error(data.message(), null, data.code()))
            }
        }

    }


}