package com.example.basetemplate

import android.app.Application
import com.example.basetemplate.main.manager.base.apiModule
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware

class MyApplication :Application(),KodeinAware{
    override val kodein = Kodein.lazy {
        import(apiModule)
    }

}