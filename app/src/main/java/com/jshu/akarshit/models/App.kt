package com.jashwant.mvvm_reftrofit_roomdb.models

data class App(
    val app_icon: String,
    val app_id: Int,
    val app_name: String,
    val app_package_name: String,
    val fk_kid_id: Int,
    val kid_profile_image: String,
    val status: String
)