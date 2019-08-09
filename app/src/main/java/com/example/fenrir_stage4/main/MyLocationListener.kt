package com.example.basetemplate.main

import android.content.Context
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent


public class MyLocationListener(val context: Context, val lifecycle: Lifecycle, val listener: LocationListener) : LifecycleObserver {
    var enabled = false

    init {
        lifecycle.addObserver(this)
    }


    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun start(){
        if(enabled){
            //todo connect
        }
    }


    public fun enable(){
        enabled = true
        if(lifecycle.currentState.isAtLeast(Lifecycle.State.STARTED)){
            //todo connect if not connected
        }

    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun stop(){
        //todo disconnect if connected

    }

    interface LocationListener{
        fun onLocationChanged()


    }


}