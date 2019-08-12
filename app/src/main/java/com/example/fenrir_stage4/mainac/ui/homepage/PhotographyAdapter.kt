package com.example.fenrir_stage4.mainac.ui.homepage

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter
import com.bumptech.glide.Glide
import com.example.fenrir_stage4.R
import com.example.fenrir_stage4.model.Job


class PhotographyAdapter(
    private val context: Context,
    private val list: List<Job.ImgPR>
) : PagerAdapter() {


    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val item = list[position]

        val root = LayoutInflater.from(context).inflate(R.layout.page_cell_photography, container, false)

        Glide.with(context).load(item.url).into(root.findViewById(R.id.cell_photography_img))

        //set title
        container.addView(root)
        return root
    }


    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    override fun getCount(): Int {
        return list.size
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }



}