package com.jshu.akarshit.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.jashwant.mvvm_reftrofit_roomdb.models.App
import com.jashwant.mvvm_reftrofit_roomdb.models.AppList
import com.jshu.akarshit.api.ApiService
import com.jshu.akarshit.api.AppDao
import com.jshu.akarshit.models.AppEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AppListRepository(
    private val api: ApiService,
    private val dao: AppDao
) {

    fun getApps(): Flow<List<AppEntity>> =
        dao.getApps()

    suspend fun refreshApps(kidId: Int) {
        try {
            val response = api.getapplist(kidId)

            if (response.isSuccessful) {
                response.body()?.let { appList ->

                    val entities = appList.data.app_list.map { app ->
                        AppEntity(
                            appId = app.app_id,
                            appName = app.app_name,
                            appIcon = app.app_icon,
                            packageName = app.app_package_name,
                            status = app.status
                        )
                    }

                    dao.insertApps(entities)

                    Log.d("Repository", "Inserted ${entities.size} apps")
                }
            } else {
                Log.e("Repository", "API error: ${response.code()}")
            }
        } catch (e: Exception) {
            Log.e("Repository", "Network error", e)
        }
    }
}