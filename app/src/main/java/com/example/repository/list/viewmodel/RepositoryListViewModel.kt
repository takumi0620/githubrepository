package com.example.repository.list.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.room.Room
import com.example.repository.list.model.Database
import com.example.repository.list.model.api.ApiManager
import com.example.repository.list.model.data.RepositoryData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class RepositoryListViewModel(application: Application) : AndroidViewModel(application) {
    val dataList = MutableLiveData<Array<RepositoryData>>()
    val isShowProgress = MutableLiveData<Boolean>()
    private val job = Job()
    private val default = job + Dispatchers.Default
    private val main = job + Dispatchers.Main
    private val db = Room.databaseBuilder(application, Database::class.java, "example").build()

    fun getDataFromGithubApi(userName: String) {
        isShowProgress.value = true
        ApiManager.getRepositoryFromUserName(userName, object : ApiManager.CallbackListener<Array<RepositoryData>> {
            override fun onSuccess(result: Array<RepositoryData>) {
                CoroutineScope(default).launch {
                    result.forEach { data ->
                        db.repositoryDataDao().createRepositoryData(data)
                    }
                }
                dataList.value = result
            }

            override fun onFailure(message: String) {
                CoroutineScope(default).launch {
                    val cachedData = db.repositoryDataDao().readRepositoryDataFindByUserName(userName)
                    CoroutineScope(main).launch {
                        dataList.value = cachedData
                    }
                }
            }

            override fun onFinally() {
                isShowProgress.value = false
            }
        })
    }

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }
}