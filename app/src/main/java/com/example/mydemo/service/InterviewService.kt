package com.example.mydemo.service

import com.example.mydemo.model.VectorResponse
import io.reactivex.Single
import retrofit2.http.GET

interface InterviewService {

  @GET("/interview/interview_get_vector.json")
  fun getVector(): Single<VectorResponse>

}