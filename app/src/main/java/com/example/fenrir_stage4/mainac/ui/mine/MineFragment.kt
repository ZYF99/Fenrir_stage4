package com.example.fenrir_stage4.mainac.ui.mine

import android.annotation.SuppressLint
import android.view.View
import android.widget.TextView
import com.example.fenrir_stage4.mainac.ui.widget.DatePopView
import com.example.fenrir_stage4.mainac.ui.widget.JobPopView
import com.example.fenrir_stage4.base.BindingFragment
import com.example.fenrir_stage4.R
import com.example.fenrir_stage4.databinding.MineBinding
import com.example.fenrir_stage4.mainac.ui.widget.BottomDialogView
import com.example.fenrir_stage4.mainac.ui.widget.CityPopView
import com.example.fenrir_stage4.mainac.utils.JudgeUtil
import com.jakewharton.rxbinding2.view.RxView

class MineFragment : BindingFragment<MineBinding, MineViewModel>(
    MineViewModel::class.java, R.layout.fragment_mine
) {

    override fun initBefore() {

    }

    override fun initWidget(view: View) {
        RxView.clicks(binding.tvDate)
            .doOnNext {
                createDatePop()
            }.bindLife()
        RxView.clicks(binding.tvCity)
            .doOnNext {
                createCityPop()
            }.bindLife()
        RxView.clicks(binding.tvWork)
            .doOnNext {
                createJobPop()
            }.bindLife()
        RxView.clicks(binding.btnSubmit)
            .doOnNext {
                submit()
            }.bindLife()
    }

    override fun initData() {

    }

    //create pop of datePicker
    private fun createDatePop() {
        hideKeyboard()
        binding.btndatedown.setImageResource(R.drawable.icn_chevron_down_black)
        val pop = context?.let { DatePopView(it) }
        pop?.show()
        //pop click listener
        pop?.setOnClickListener(object : BottomDialogView.OnMyClickListener {
            @SuppressLint("SetTextI18n")
            override fun onFinishClick() {
                binding.tvDate.text = pop.years + pop.months + pop.days
            }
        })

    }

    //create pop of jobPicker
    private fun createJobPop() {
        hideKeyboard()
        binding.btnjobdown.setImageResource(R.drawable.icn_chevron_down_black)
        val pop = context?.let { JobPopView(it) }
        pop?.show()
        //pop click listener
        pop?.setOnClickListener(object : BottomDialogView.OnMyClickListener {
            @SuppressLint("SetTextI18n")
            override fun onFinishClick() {
                binding.tvWork.text = pop.job
            }
        })

    }

    //create pop of cityPicker
    private fun createCityPop() {
        hideKeyboard()
        binding.btncitydown.setImageResource(R.drawable.icn_chevron_down_black)
        val pop = context?.let { CityPopView(it) }
        pop?.show()
        //pop click listener
        pop?.setOnClickListener(object : BottomDialogView.OnMyClickListener {
            @SuppressLint("SetTextI18n")
            override fun onFinishClick() {
                binding.tvCity.text = pop.city
            }
        })
    }

    //do submit
    private fun submit() {
        judge()
    }

    //judge input
    private fun judge() {
        if (binding.editName.text.isEmpty()) {
            showError(binding.editName, binding.errorName)
        } else {
            hideError(binding.editName, binding.errorName)
        }
        if (binding.editPetname.text.isEmpty()) {
            showError(binding.editPetname, binding.errorPetname)
        } else {
            hideError(binding.editPetname, binding.errorPetname)
        }
        if (binding.tvDate.text.isEmpty()) {
            showError(binding.tvDate, binding.errorDate)
        } else {
            hideError(binding.tvDate, binding.errorDate)
        }
        if (binding.editTel.text.isEmpty() || binding.editTel.length() < 10) {
            showError(binding.editTel, binding.errorTel)
        } else {
            hideError(binding.editTel, binding.errorTel)
        }
        if (binding.editEmail.text.isEmpty() || !(JudgeUtil.isEmail(binding.editEmail.text.toString()))) {
            showError(binding.editEmail, binding.errorEmail)
        } else {
            hideError(binding.editEmail, binding.errorEmail)
        }
        if (binding.tvWork.text.isEmpty()) {
            showError(binding.tvWork, binding.errorJob)
        } else {
            hideError(binding.tvWork, binding.errorJob)
        }
        if (binding.tvCity.text.isEmpty()) {
            showError(binding.tvCity, binding.errorCity)
        } else {
            hideError(binding.tvCity, binding.errorCity)
        }
    }

    @SuppressLint("NewApi")
    private fun showError(inputView: View, errorTextView: TextView) {
        inputView.background = context?.resources!!.getDrawable(R.drawable.bg_inputlin_error)
        errorTextView.visibility = View.VISIBLE
    }

    @SuppressLint("NewApi")
    private fun hideError(inputView: View, errorTextView: TextView) {
        inputView.background = context?.resources!!.getDrawable(R.drawable.bg_inputlin_idle)
        errorTextView.visibility = View.INVISIBLE
    }


}