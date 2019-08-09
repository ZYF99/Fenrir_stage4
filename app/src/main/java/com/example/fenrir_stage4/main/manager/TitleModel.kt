package com.example.basetemplate.main.manager

import java.io.Serializable

data class TitleModel(
    val titleTxt: String?,
    val address: String?,
    val level: Int?
) : Serializable