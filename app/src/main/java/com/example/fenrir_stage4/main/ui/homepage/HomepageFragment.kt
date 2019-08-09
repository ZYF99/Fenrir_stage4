package com.example.fenrir_stage4.main.ui.homepage

import android.content.Context
import android.net.ConnectivityManager
import android.view.View
import com.example.basetemplate.main.BindingFragment
import com.example.basetemplate.main.ui.homepage.HomepageViewModel
import com.example.fenrir_stage4.R
import com.example.fenrir_stage4.databinding.HomepageBinding
import com.google.android.material.snackbar.Snackbar
import com.jakewharton.rxbinding2.support.v4.widget.RxSwipeRefreshLayout
import io.reactivex.android.schedulers.AndroidSchedulers


class HomepageFragment : BindingFragment<HomepageBinding, HomepageViewModel>(
    HomepageViewModel::class.java, R.layout.fragment_homepage
) {

    override fun initBefore() {


    }

    override fun initWidget(view: View) {

        binding.vm = viewModel

        RxSwipeRefreshLayout.refreshes(binding.refreshlayout)
            .observeOn(AndroidSchedulers.mainThread())
            .doOnNext {

                print("sdsdsdsds"+viewModel.toString())
                when {
                    !isNetworkAvailable() -> {
                        showNetErrorSnackBar()
                    }
                    else -> {
                        viewModel.refreshing.value = true
                        viewModel.getJobList()
                    }
                }
            }
            .bindLife()
    }

    override fun initData() {

    }

    private fun isNetworkAvailable() =
        (context?.getSystemService(Context.CONNECTIVITY_SERVICE) as? ConnectivityManager)?.activeNetworkInfo?.isConnected
            ?: false


    private fun showNetErrorSnackBar() {
        binding.root.post {
            Snackbar.make(
                binding.root,
                R.string.net_unavailable,
                Snackbar.LENGTH_LONG
            ).show()
        }
    }
}