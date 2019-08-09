package com.example.basetemplate.main.ui.homepage

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.example.basetemplate.main.manager.TitleModel
import com.example.basetemplate.ui.base.BaseViewModel
import io.reactivex.SingleTransformer

class MainViewModel(application: Application): BaseViewModel(application){


    //private val apiService:JobService by instance<JobService>

    val refreshing = MutableLiveData<Boolean>()

    //todo import kodelin
    //private val HomapageService by

    val titleModel = MutableLiveData<TitleModel>()


/*    fun getTitleModel(){

        apiService.getHomepageTitle()
            .subscribeOn(Schedulers.io())
            .compose(dealError())
            .bindLife()




    }*/

    private fun <T> dealRefresh(): SingleTransformer<T, T> {
        return SingleTransformer { obs ->
            obs.doOnSubscribe { refreshing.postValue(true) }
                .doOnSuccess { refreshing.postValue(false) }
                .doOnError { refreshing.postValue(false) }
        }
    }

}