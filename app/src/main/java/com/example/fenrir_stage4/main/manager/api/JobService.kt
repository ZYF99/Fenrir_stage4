package com.example.basetemplate.main.manager.api

import com.example.fenrir_stage4.model.Job
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface JobService {


    @GET("jobs?")
    fun getJobList(@Query("offset") offset: Int): Single<List<Job>>


    @GET("job?")
    fun getJob(@Query("jobId") Id: String): Single<Job>


}