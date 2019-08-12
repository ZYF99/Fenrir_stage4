package com.example.fenrir_stage4.collectionac.ui

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.example.fenrir_stage4.base.BaseViewModel
import com.example.fenrir_stage4.manager.api.JobService
import com.example.fenrir_stage4.model.Job
import com.example.fenrir_stage4.utils.SharedPreferencesUtil
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.kodein.di.generic.instance


class CollectionViewModel(application: Application) : BaseViewModel(application) {

    private val jobService by instance<JobService>()
    val jobList = MutableLiveData<MutableList<Job>>()
    val count = MutableLiveData<String>().default("0件")


    fun init() {
        jobList.value = mutableListOf()
        getJobListFromIds()
    }


    private fun getJobListFromIds() {
        val idList = SharedPreferencesUtil.getListData("collections", String::class.java)

        Observable.fromIterable(idList)
            .subscribeOn(Schedulers.io())
            .flatMapSingle {
                jobService.getJob(it)
            }
            .toList()
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSuccess {
                jobList.value = it
                count.value = "${idList.size}件"
            }.bindLife()
    }

    fun deleteJobInIds(pos: Int) {
        val idList = SharedPreferencesUtil.getListData("collections", String::class.java)
        val list = jobList.value
        list!!.removeAt(pos)
        jobList.postValue(list)
        idList.removeAt(pos)
        count.value = "${idList.size}件"
        SharedPreferencesUtil.putListData("collections", idList)
    }

}