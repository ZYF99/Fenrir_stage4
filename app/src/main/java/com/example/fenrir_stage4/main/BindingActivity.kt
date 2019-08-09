package com.example.common.app


import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil

import androidx.databinding.ViewDataBinding
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModelProviders
import com.example.basetemplate.ui.base.BindLife
import io.reactivex.disposables.CompositeDisposable


abstract class BindingActivity<Bind : ViewDataBinding, VM : AndroidViewModel> : AppCompatActivity(), BindLife {

    abstract val clazz: Class<VM>
    abstract val layRes: Int

    val viewModel: VM by lazy {
        ViewModelProviders.of(
            this
            //,ViewModelProvider.AndroidViewModelFactory.getInstance(activity!!.application)
        ).get(clazz)
    }

    lateinit var binding: Bind

    override val compositeDisposable = CompositeDisposable()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, layRes)
        initBefore()
        initWidget()
        initData()
    }


    //something init before initWidget
    abstract fun initBefore()

    //widget init
    abstract fun initWidget()

    //data init
    abstract fun initData()


    //Fragment's destroy function
    override fun onDestroy() {
        destroyDisposable()
        super.onDestroy()
    }

    //hide keyBoard
    fun hideKeyboard() {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
        if (imm!!.isActive)
            imm.hideSoftInputFromWindow(
                currentFocus?.windowToken,
                InputMethodManager.HIDE_NOT_ALWAYS
            )
    }


}
