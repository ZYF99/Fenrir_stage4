package com.example.fenrir_stage4.mainac.ui


import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction.TRANSIT_FRAGMENT_FADE
import com.example.fenrir_stage4.mainac.ui.homepage.HomepageFragment
import com.example.fenrir_stage4.mainac.ui.mine.MineFragment
import com.example.fenrir_stage4.mainac.utils.ErrorType
import com.example.fenrir_stage4.mainac.utils.getErrorObs
import com.example.fenrir_stage4.mainac.utils.showNoWifiDialog
import com.example.fenrir_stage4.mainac.utils.showUnexpectedDialog
import com.example.fenrir_stage4.base.BindingActivity
import com.example.fenrir_stage4.R
import com.example.fenrir_stage4.collectionac.ui.CollectionActivity
import com.example.fenrir_stage4.databinding.MainBinding
import com.example.fenrir_stage4.model.Job
import com.jakewharton.rxbinding2.view.RxView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import timber.log.Timber
import java.util.concurrent.TimeUnit
import rx_activity_result2.RxActivityResult




@Suppress("UNCHECKED_CAST")
class MainActivity : BindingActivity<MainBinding, MainViewModel>() {


    override val clazz: Class<MainViewModel> = MainViewModel::class.java
    override val layRes: Int = R.layout.activity_main

    private var errorDisposable: Disposable? = null
    private var errorDialog: AlertDialog? = null

    var fragment: Fragment? = null


    override fun initBefore() {

    }

    @SuppressLint("ResourceType")
    override fun initWidget() {
        //Tab event
        RxView.clicks(binding.tab1)
            .throttleFirst(1, TimeUnit.SECONDS)
            .doOnNext {
                changeTab(1)
            }.doOnSubscribe {
                //click tab1 when widget init
                changeTab(1)
            }
            .bindLife()
        RxView.clicks(binding.tab2)
            .throttleFirst(1, TimeUnit.SECONDS)
            .doOnNext {
                changeTab(2)
            }
            .bindLife()

        RxView.clicks(binding.btnCollection)
            .throttleFirst(1, TimeUnit.SECONDS)
            .doOnNext {
                startByRxActivityResult()
            }
            .bindLife()

        //resolve error
        handleError()


    }

    override fun initData() {

    }

    @SuppressLint("NewApi")
    private fun changeTab(tab: Int) {


        when (tab) {
            1 -> {
                binding.tab1.setTextColor(resources.getColor(R.color.colorWhite,theme))
                binding.tab1.setBackgroundResource(R.drawable.bg_tab_1_selected)
                binding.tab2.setTextColor(resources.getColor(R.color.colorAccent,theme))
                binding.tab2.setBackgroundResource(R.drawable.bg_tab_2_nor)
                fragment = HomepageFragment()

            }
            2 -> {
                binding.tab1.setTextColor(resources.getColor(R.color.colorAccent,theme))
                binding.tab1.setBackgroundResource(R.drawable.bg_tab_1_nor)
                binding.tab2.setTextColor(resources.getColor(R.color.colorWhite,theme))
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

    private fun startByRxActivityResult() {
        RxActivityResult.on(this)
            .startIntent(Intent(this, CollectionActivity::class.java))
            .map { result -> result.data() }
            .doOnNext {
                if(fragment is HomepageFragment){

/*                    val deleteArray:MutableList<String> = it.getSerializableExtra("deleteArray") as MutableList<String>
                    val jobList = (fragment as HomepageFragment).viewModel.jobList.value


                    val newList = jobList!!.flatMap {job ->
                        deleteArray.map {str ->
                            if(job.id == str){
                                job.isCollected = false

                            }
                        }
                        jobList
                    } as MutableList<Job>

                    (fragment as HomepageFragment).viewModel.jobList.value = newList*/

                    (fragment as HomepageFragment).viewModel.init()

                }
            }.bindLife()
    }

}
