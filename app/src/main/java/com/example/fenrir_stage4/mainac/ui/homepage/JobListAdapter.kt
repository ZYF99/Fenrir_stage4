package com.example.fenrir_stage4.mainac.ui.homepage

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.example.fenrir_stage4.R
import com.example.fenrir_stage4.databinding.HomepageItemLayoutBinding
import com.example.fenrir_stage4.model.Job


class JobListAdapter(
    private val context: Context,
    private var list: MutableList<Job>,
    private val onCollectionClick: (Int) -> Unit
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    //Viewbinding Create
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = DataBindingUtil.inflate<HomepageItemLayoutBinding>(
            LayoutInflater.from(parent.context),
            R.layout.homepage_item_layout,
            parent,
            false
        )
        //val view = LayoutInflater.from(parent.context).inflate(R.layout.homepage_item_layout, parent, false)
        return ViewHolder(binding,onCollectionClick)
    }

    //Data binding
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ViewHolder).onBind(context, list[position])

        //collect button click event
        holder.collectionBtn.setOnClickListener {
            onCollectionClick.invoke(position)
            triggerCollectionItem(list[position])
        }
    }


    open class ViewHolder(
        private val binding: HomepageItemLayoutBinding,
        private val onCollectionClick: (Int) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        //the last stationImage current position
        private var oldStationPage = -1

        val collectionBtn = binding.collectBtn

        @SuppressLint("SetTextI18n")
        fun onBind(
            context: Context,
            item: Job
        ) {
            when (item.new_flg) {
                "0" -> {
                    //not new
                    binding.linNew.visibility = View.GONE
                    binding.baseTvTitle.visibility = View.VISIBLE
                    binding.baseTvTitle.text = item.txt_title + item.txt
                }
                "1" -> {
                    //new
                    binding.linNew.visibility = View.VISIBLE
                    binding.baseTvTitle.visibility = View.GONE
                    binding.newTvTitle.text = item.txt_title
                    binding.newTvTxt.text = item.txt
                }
            }

            var imgId = 100
            when (item.emp.id) {
                "100" -> {
                    imgId = R.drawable.icn_job_regular
                }
                "110" -> {
                    imgId = R.drawable.icn_job_fresh
                }
                "120" -> {
                    imgId = R.drawable.icn_job_part
                }
                "130" -> {
                    imgId = R.drawable.icn_job_dispatch
                }
                "140" -> {
                    imgId = R.drawable.icn_job_intro
                }
                "150" -> {
                    imgId = R.drawable.icn_job_delegation
                }
                "160" -> {
                    imgId = R.drawable.icn_job_agreement
                }
                "170" -> {
                    imgId = R.drawable.icn_job_irregular
                }
                "180" -> {
                    imgId = R.drawable.icn_job_agency
                }
                "199" -> {
                    imgId = R.drawable.icn_job_other
                }
            }

            binding.tvOption.text = item.option


            var subText = ""
            if (item.option.isNotEmpty()) {
                for (i in 0..item.option.length) {
                    subText += "    "
                }
            }

            binding.tvSub.text = "$subText${item.sub.name + "(" + item.master.name + ")"}"

            binding.imEmp.setImageResource(imgId)
            binding.tvPhrase.text = item.phrase
            binding.tvOccgName.text = item.occ_g.name + "(" + item.occ.name + ")"
            binding.tvSal.text = item.sal_txt
            binding.tvPref.text = item.pref.name + item.city.name + item.addr + item.addr_etc
            var s = ""
            item.station.map { s += it.name }
            binding.tvStationName.text = s
            val datArr = item.pubend_dat.split("-".toRegex())
            binding.tvPubend.text = "[掲載終了日]${datArr[0]}年${datArr[1]}月${datArr[2]}日"

            when {
                item.pubend_days == "0" -> {
                    binding.tvEndtoday.visibility = View.VISIBLE
                    binding.linPubend.visibility = View.GONE
                }
                item.pubend_days == "" -> {
                    binding.tvEndtoday.visibility = View.GONE
                    binding.linPubend.visibility = View.GONE
                }
                item.pubend_days.toInt() >= 1 -> {
                    binding.linPubend.visibility = View.VISIBLE
                    binding.tvEndtoday.visibility = View.GONE
                    binding.tvPubendDay.text = item.pubend_days
                }
            }

            //workstation block
            if (item.img.isEmpty()) {
                binding.linStation.visibility = View.GONE
            } else {
                binding.linStation.visibility = View.VISIBLE
                oldStationPage = -1

                val stationAdapter = ImagePagerAdapter(context, item.img, binding.workstationpager)
                binding.workstationpager.adapter = stationAdapter
                //init stationBtn
                if (item.img.size <= 1) {
                    binding.btnStationNext.visibility = View.GONE
                } else {
                    binding.btnStationNext.visibility = View.VISIBLE
                }
                //workstation btnNext click event
                binding.btnStationNext.setOnClickListener {
                    //do workstation imageList loop
                    val current = binding.workstationpager.currentItem
                    when {
                        current > oldStationPage -> {
                            when {
                                current + 1 > item.img.size - 1 -> binding.workstationpager.currentItem =
                                    current - 1
                                else -> binding.workstationpager.currentItem = current + 1
                            }
                        }
                        else -> {
                            when {
                                current - 1 < 0 -> binding.workstationpager.currentItem = current + 1
                                else -> binding.workstationpager.currentItem = current - 1
                            }
                        }
                    }
                    oldStationPage = current
                }
            }

            //photography block
            if (item.img_pr.isEmpty()) {
                binding.linPhotography.visibility = View.GONE
            } else {
                binding.linPhotography.visibility = View.VISIBLE
                val adapterPhotography = PhotographyAdapter(context, item.img_pr)
                binding.photographypager.adapter = adapterPhotography
                //init PrBtn
                binding.photographyLastBtn.visibility = View.INVISIBLE
                if (item.img_pr.size <= 1) binding.photographyNextBtn.visibility = View.INVISIBLE


                binding.photographypager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
                    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

                    }

                    override fun onPageSelected(position: Int) {

                    }

                    override fun onPageScrollStateChanged(state: Int) {

                        //check if it has more, no:hide the btnNext
                        if (binding.photographypager.currentItem + 1 > item.img_pr.size - 1) {
                            binding.photographyNextBtn.visibility = View.INVISIBLE
                        } else {
                            binding.photographyNextBtn.visibility = View.VISIBLE
                        }
                        //check if it has last, no:hide the btnLast
                        if (binding.photographypager.currentItem - 1 < 0) {
                            binding.photographyLastBtn.visibility = View.INVISIBLE
                        } else {
                            binding.photographyLastBtn.visibility = View.VISIBLE
                        }
                    }

                })


                //pr btn click event -> last
                binding.photographyNextBtn.setOnClickListener {
                    //check if it can click next
                    if (binding.photographypager.currentItem + 1 <= item.img_pr.size - 1) {
                        binding.photographypager.currentItem += 1
                        binding.photographyLastBtn.visibility = View.VISIBLE
                    }
                }
                //pr btn click event -> next
                binding.photographyLastBtn.setOnClickListener {
                    //check if it has last
                    if (binding.photographypager.currentItem - 1 >= 0) {
                        binding.photographypager.currentItem -= 1
                        binding.photographyNextBtn.visibility = View.VISIBLE
                    }
                }
            }



            if (item.isCollected) {

                binding.tvCollect.text = "キープ中"
                binding.collectBtn.setBackgroundResource(R.drawable.bg_btn_keep_true)
                binding.imCollect.setImageResource(R.drawable.icn_kept_on_btn)
            } else {
                binding.tvCollect.text = "キープする"
                binding.collectBtn.setBackgroundResource(R.drawable.bg_btn_keep_false)
                binding.imCollect.setImageResource(R.drawable.icn_keep_border_yellow)
            }
        }





    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun addAll(list: Collection<Job>) {

        this.list.addAll(list)
        notifyDataSetChanged()
    }

    fun replaceAll(list: Collection<Job>) {
        this.list.clear()
        this.list.addAll(list)
        notifyDataSetChanged()
    }

    fun addItem(job: Job) {
        this.list.add(job)
        notifyDataSetChanged()
    }

    fun removeItem(job: Job) {
        this.list.remove(job)
        notifyDataSetChanged()
    }


    private fun triggerCollectionItem(job: Job) {

        job.isCollected = !job.isCollected

        notifyDataSetChanged()
    }


}