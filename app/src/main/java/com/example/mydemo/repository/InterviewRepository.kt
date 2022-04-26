package com.example.mydemo.repository

import com.example.mydemo.service.InterviewService
import com.example.mydemo.extension.ioToUi

class InterviewRepository(private val interviewService: InterviewService) {
  fun getVector() = interviewService.getVector().ioToUi()
}