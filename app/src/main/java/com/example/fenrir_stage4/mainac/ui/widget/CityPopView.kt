package com.example.fenrir_stage4.mainac.ui.widget

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import com.example.common.widget.WheelView
import com.example.fenrir_stage4.R

class CityPopView(context: Context) : BottomDialogView(context) {

    var city = ""
    private lateinit var wheelView1: WheelView

    @SuppressLint("InflateParams")
    override var bView = LayoutInflater.from(context).inflate(R.layout.citypickerview,null)!!

    override fun initView() {
        wheelView1 = bView.findViewById(R.id.city)
        btnFinish = bView.findViewById(R.id.btn_finish)


        val cityList = mutableListOf("北海道","青森県","岩手県","宫城県","秋田県","山形県","福岛県")
        city = cityList[cityList.size/2]
        wheelView1.setData(cityList)

        //wheel select event
        wheelView1.setOnSelectListener(object : WheelView.OnSelectListener {
            override fun onSelect(text: String) {
                city = text
            }
        })
    }









}