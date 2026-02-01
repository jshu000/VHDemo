package com.jshu.akarshit.api

import com.jashwant.mvvm_reftrofit_roomdb.models.AppList
import retrofit2.Response
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {

    @POST("g-mee-api/api/v1/apps/list")
    suspend fun getapplist(@Query("kid_id") q: Int ): Response<AppList>
}