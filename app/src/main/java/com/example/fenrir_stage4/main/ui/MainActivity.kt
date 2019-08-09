package com.example.fenrir_stage4.main.ui


import android.annotation.SuppressLint
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction.TRANSIT_FRAGMENT_FADE
import com.example.fenrir_stage4.main.ui.homepage.HomepageFragment
import com.example.basetemplate.main.ui.homepage.MainViewModel
import com.example.basetemplate.main.ui.mine.MineFragment
import com.example.basetemplate.main.util.ErrorType
import com.example.basetemplate.main.util.getErrorObs
import com.example.basetemplate.main.util.showNoWifiDialog
import com.example.basetemplate.main.util.showUnexpectedDialog
import com.example.common.app.BindingActivity
import com.example.fenrir_stage4.R
import com.example.fenrir_stage4.databinding.MainBinding
import com.jakewharton.rxbinding2.view.RxView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import timber.log.Timber
import java.util.concurrent.TimeUnit


class MainActivity : BindingActivity<MainBinding, MainViewModel>() {


    override val clazz: Class<MainViewModel> = MainViewModel::class.java
    override val layRes: Int = R.layout.activity_main

    private var errorDisposable: Disposable? = null
    private var errorDialog: AlertDialog? = null



    override fun initBefore() {

    }


    @SuppressLint("ResourceType")
    override fun initWidget() {


        //Tab event
        RxView.clicks(binding.tab1)
            .throttleFirst(1,TimeUnit.SECONDS)
            .doOnNext {
                changeTab(1)
            }.doOnSubscribe {
                //click tab1 when widget init
                changeTab(1)
            }
            .bindLife()
        RxView.clicks(binding.tab2)
            .throttleFirst(1,TimeUnit.SECONDS)
            .doOnNext {
                changeTab(2)
            }
            .bindLife()

        //resolve error
        handleError()


    }

    override fun initData() {

    }

    private fun changeTab( tab:Int){

        var fragment:Fragment?= null
        when(tab){
            1 -> {
                binding.tab1.setTextColor(resources.getColor(R.color.colorWhite))
                binding.tab1.setBackgroundResource(R.drawable.bg_tab_1_selected)
                binding.tab2.setTextColor(resources.getColor(R.color.colorAccent))
                binding.tab2.setBackgroundResource(R.drawable.bg_tab_2_nor)
                fragment = HomepageFragment()

            }
            2 -> {
                binding.tab1.setTextColor(resources.getColor(R.color.colorAccent))
                binding.tab1.setBackgroundResource(R.drawable.bg_tab_1_nor)
                binding.tab2.setTextColor(resources.getColor(R.color.colorWhite))
                binding.tab2.setBackgroundResource(R.drawable.bg_tab_2_selected)
                fragment = MineFragment()
            }
        }
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.maincontainer, fragment!!)
            setTransition(TRANSIT_FRAGMENT_FADE)
            commit()
        }

    }

    //实际'异常'处理者
    private fun handleError() {

        errorDisposable = getErrorObs()
            .observeOn(AndroidSchedulers.mainThread())
            .doOnNext {
                if (!errorDialog!!.isShowing) {
                    errorDialog = when (it.errorType) {
                        ErrorType.NO_WIFI -> showNoWifiDialog(this) {}
                        else -> showUnexpectedDialog(this)
                    }
                }
            }.subscribe({}, { Timber.e(it) })

    }

    override fun onDestroy() {
        errorDialog?.dismiss()
        errorDialog = null
        errorDisposable?.dispose()
        super.onDestroy()
    }


}
