package com.example.fenrir_stage4.mainac.ui.homepage

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fenrir_stage4.base.BindingFragment
import com.example.fenrir_stage4.R
import com.example.fenrir_stage4.databinding.HomepageBinding
import com.google.android.material.snackbar.Snackbar
import com.jakewharton.rxbinding2.support.v4.widget.RxSwipeRefreshLayout


class HomepageFragment : BindingFragment<HomepageBinding, HomepageViewModel>(
    HomepageViewModel::class.java, R.layout.fragment_homepage
) {
    override fun initBefore() {


    }

    @SuppressLint("CheckResult")
    override fun initWidget(view: View) {
        binding.vm = viewModel
        viewModel.refreshing.observe { binding.refreshlayout.isRefreshing = it!! }

        viewModel.init()

        binding.mainrec.run {
            layoutManager = LinearLayoutManager(context)
            adapter = JobListAdapter(context, viewModel.jobList.value!!, onCollectionClick = {
                viewModel.collectJob(it)
            })
        }


        RxSwipeRefreshLayout.refreshes(binding.refreshlayout)
            .doOnNext {
                when {
                    !isNetworkAvailable() -> {
                        showNetErrorSnackBar()
                    }
                    else -> {
                        viewModel.getJobList()
                    }
                }
            }
            .bindLife()



        viewModel.jobList.observe {
            binding.mainrec.run {
                (adapter as JobListAdapter).replaceAll(it!!)
            }
        }

    }

    override fun initData() {

    }

    fun updateListFromDeletes(deleteList: MutableList<String>) {
        val jobList = viewModel.jobList.value
        jobList!!.forEach { job ->
            deleteList.forEach { str ->
                if (job.id == str) {
                    job.isCollected = false
                }
            }
        }
        viewModel.jobList.value = jobList
    }




    private fun isNetworkAvailable() =
        (context?.getSystemService(Context.CONNECTIVITY_SERVICE) as? ConnectivityManager)?.activeNetworkInfo?.isConnected
            ?: false

    private fun showNetErrorSnackBar() {
        Snackbar.make(
            binding.root,
            R.string.net_unavailable,
            Snackbar.LENGTH_LONG
        ).show()
    }
}