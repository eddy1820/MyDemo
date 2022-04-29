package com.example.mydemo.service

import com.example.mydemo.model.VectorResponse
import com.example.mydemo.model.VectorResponseFail
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.GET

interface InterviewService {

    @GET("/interview/interview_get_vector.json")
    fun getVector(): Single<VectorResponse>

    @GET("/interview/interview_get_vector.json")
    suspend fun getVectorWithFlow(): Response<VectorResponse>

    @GET("/interview/interview_get_vector.json12")
    suspend fun getVectorWithFlow2(): Response<VectorResponse>

    @GET("/interview/interview_get_vector.json")
    suspend fun getVectorWithFlow3(): Response<VectorResponseFail>
}