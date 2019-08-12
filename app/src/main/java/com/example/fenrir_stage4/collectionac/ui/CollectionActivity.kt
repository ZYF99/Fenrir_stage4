package com.example.fenrir_stage4.collectionac.ui


import android.app.Activity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fenrir_stage4.R
import com.example.fenrir_stage4.base.BindingActivity
import com.example.fenrir_stage4.databinding.CollectionBinding
import com.example.fenrir_stage4.mainac.ui.homepage.JobListAdapter
import com.jakewharton.rxbinding2.view.RxView
import java.io.Serializable


class CollectionActivity : BindingActivity<CollectionBinding, CollectionViewModel>() {


    private val deleteIdArray = mutableListOf<String>()
    override val clazz: Class<CollectionViewModel> = CollectionViewModel::class.java
    override val layRes: Int = R.layout.activity_collection



    override fun initBefore() {

    }

    override fun initWidget() {
        binding.vm = viewModel
        viewModel.init()
        binding.recCollection.run {
            layoutManager = LinearLayoutManager(this@CollectionActivity)
            adapter = JobListAdapter(this@CollectionActivity, viewModel.jobList.value!!, onCollectionClick = { pos ->
                deleteIdArray.add((viewModel.jobList.value)!![pos].id)
                viewModel.deleteJobInIds(pos)
            })
        }


        viewModel.jobList.observeForever {
            binding.recCollection.run {
                (adapter as JobListAdapter).replaceAll(it!!)
            }
        }

        viewModel.count.observeForever {
            binding.tvTitleSec.text = it
        }

        RxView
            .clicks(binding.btnBack)
            .doOnNext {
                goBack()
            }
            .bindLife()
    }

    override fun initData() {

    }

    override fun onBackPressed() {
        goBack()
    }
    private fun goBack(){
        val intent = intent
        val bundle = Bundle()
        bundle.putSerializable("deleteArray",deleteIdArray as Serializable)
        intent.putExtras(bundle)
        setResult(Activity.RESULT_OK, intent)
        finish()
    }



}
