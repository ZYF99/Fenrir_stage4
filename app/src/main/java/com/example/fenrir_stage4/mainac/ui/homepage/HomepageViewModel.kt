package com.example.fenrir_stage4.mainac.ui.homepage

import android.app.Application
import android.os.Handler
import androidx.lifecycle.MutableLiveData
import com.example.fenrir_stage4.manager.api.JobService
import com.example.fenrir_stage4.base.BaseViewModel
import com.example.fenrir_stage4.model.Job
import com.example.fenrir_stage4.utils.SharedPreferencesUtil
import io.reactivex.Single
import io.reactivex.SingleTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.kodein.di.generic.instance
import java.util.concurrent.TimeUnit


class HomepageViewModel(application: Application) : BaseViewModel(application) {


    private val jobService by instance<JobService>()

    val jobList = MutableLiveData<MutableList<Job>>()

    val refreshing = MutableLiveData<Boolean>()


    fun init() {
        jobList.value = mutableListOf()
        getJobList()

    }



    fun getJobList() {
        jobService.getJobList(0)
            .compose(dealRefresh())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSuccess { it ->
                val collectionList = SharedPreferencesUtil.getListData("collections", String::class.java)
                it.map {
                    if(collectionList.contains(it.id)){
                        it.isCollected = true
                    }
                }
                jobList.value = it as MutableList<Job>?
            }
            .compose(dealError())
            .bindLife()
    }

    fun collectJob(pos: Int) {
        val list = SharedPreferencesUtil.getListData("collections", String::class.java)
        val job = jobList.value!![pos]
        if (job.isCollected) {
            list.remove(job.id)
        } else {
            list.add(job.id)
        }
        SharedPreferencesUtil.putListData("collections", list)
    }


    private fun <T> dealRefresh(): SingleTransformer<T, T> {
        return SingleTransformer { obs ->
            obs
                .doOnSubscribe { refreshing.postValue(true) }
                .doOnSuccess { refreshing.postValue(false) }
                .doOnError { refreshing.postValue(false) }
        }
    }



}