package com.example.basetemplate.main.manager.base

import com.example.basetemplate.main.manager.api.JobService
import com.example.fenrir_stage4.BuildConfig
import okhttp3.logging.HttpLoggingInterceptor
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton
import java.util.concurrent.TimeUnit

val apiModule = Kodein.Module {
    bind<ApiClient>() with singleton { provideApiClient() }
    bind<JobService>() with singleton { instance<ApiClient>().createService(JobService::class.java) }
}

fun provideApiClient(): ApiClient {
    val client = ApiClient.Builder()
    val logInterceptor = HttpLoggingInterceptor()
    logInterceptor.level = HttpLoggingInterceptor.Level.BODY

    client.okBuilder
/*        .addInterceptor(HeaderInterceptor())
        .addInterceptor(NetErrorInterceptor(globalMoshi))*/
        .apply {
            if (BuildConfig.DEBUG)
                addInterceptor(logInterceptor)
        }
        .readTimeout(30, TimeUnit.SECONDS)

    return client.build()
}