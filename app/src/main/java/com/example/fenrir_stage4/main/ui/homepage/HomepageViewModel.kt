package com.example.basetemplate.main.ui.homepage

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.basetemplate.main.manager.api.JobService
import com.example.basetemplate.ui.base.BaseViewModel
import com.example.fenrir_stage4.model.Job
import io.reactivex.SingleTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.kodein.di.generic.instance


class HomepageViewModel(application: Application): BaseViewModel(application){


    private val jobService by instance<JobService>()

    var jobList = MutableLiveData<List<Job>>()
    var refreshing = MutableLiveData<Boolean>()


    fun getJobList(){
        Log.d("SINGLE JOBSERVICE",jobService.toString())
        jobService.getJobList(0)
            .compose(dealRefresh())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSuccess {
                jobList.value = it
                print(it.toString())
            }.doOnSubscribe {
                Log.d("PPPPPPP","####################")
            }
            .compose(dealError())
            .bindLife()
    }

    private fun <T> dealRefresh(): SingleTransformer<T, T> {
        return SingleTransformer { obs ->
            obs.doOnSubscribe { refreshing.postValue(true) }
                .doOnSuccess { refreshing.postValue(false) }
                .doOnError { refreshing.postValue(false) }
        }
    }

}