package com.example.fenrir_stage4.model

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.databinding.library.baseAdapters.BR

class ObservableUser : BaseObservable() {

    @Bindable
    var name = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.name)

        }
        get() {

            return field
        }

    @Bindable
    var password = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.password)
            println(Thread.currentThread().name)

        }
        get() {

            return field
        }


}