package com.example.fenrir_stage4.main.manager.api

import com.example.fenrir_stage4.model.Job
import io.reactivex.Completable
import io.reactivex.Single
import retrofit2.http.*


interface DownloadService {

    @GET("titles")
    fun getTitles(): Single<Job>

    @GET("titles/{fullTitleId}")
    fun getTitlePack(@Path("fullTitleId") fullTitleId: String): Single<Job>


    @POST("devices")
    fun getDevices(): Completable

    @GET("url")
    fun getUrl(
        @Query("fullStoryId") fullStoryId: String?,
        @Query("fullPackId") fullPackId: String?,
        @Query("oldFullPackId") oldFullPackId: String?,
        @Query("subtitleDubType") subtitleDubType: String?
    ): Single<Job>

    @GET("license")
    fun getLicense(
        @Query("fullStoryId") fullStoryId: String?,
        @Query("fullPackId") fullPackId: String?,
        @Query("oldFullPackId") oldFullPackId: String?
    )
            : Single<Job>

    @PUT("dlcomp")
    fun putDlcomp(@Body dlcompModel: Job): Completable

    @GET("loginStatus")
    fun getLoginStatus(): Single<Job>

}