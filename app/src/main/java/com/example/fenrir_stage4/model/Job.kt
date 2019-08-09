package com.example.fenrir_stage4.model

data class Job(
    val pref: Pref
    , val sub: Sub
    , val app_url: String
    , val occ_g: Occg
    , val new_flg: String
    , val app_web: String
    , val img: List<Img>
    , val sal_txt: String
    , val app_md: String
    , val option: String
    , val lng: String
    , val city: City
    , val station: List<Station>
    , val app_tel: String
    , val id: String
    , val occ: Occ
    , val master: Master
    , val img_pr: List<ImgPR>
    , val pubend_days: String
    , val addr_etc: String
    , val emp_type: String
    , val lat: String
    , val txt: String
    , val pubend_dat: String
    , val addr: String
    , val txt_title: String
    , val emp: Emp
    , val phrase: String
    , var isCollected: Boolean = false
) {


    data class Pref(val id: String, val name: String, val roman: String)
    data class Sub(val id: String, val name: String)
    data class Occg(val id: String, val name: String)
    data class Img(val id: String, val url: String)
    data class City(val id: String, val name: String)
    data class Station(val id: String, val name: String, val txt: String)
    data class Occ(val id: String, val name: String)
    data class Master(val id: String, val name: String)
    data class ImgPR(val id: String, val url: String, val txt_id: String, val txt_title: String, val txt_count: String)
    data class Emp(val id: String, val name: String)

}

