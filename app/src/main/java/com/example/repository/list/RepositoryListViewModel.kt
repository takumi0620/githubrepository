package com.example.repository.list

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.repository.RepositoryData
import com.example.repository.api.ApiManager

class RepositoryListViewModel(app: Application) : AndroidViewModel(app) {
    val dataList = MutableLiveData<Array<RepositoryData>>()

    fun getDataFromGithubApi(userName: String) {
        ApiManager.getRepositoryFromUserName(userName, object: ApiManager.CallbackListener<Array<RepositoryData>> {
            override fun onSuccess(result: Array<RepositoryData>) {
                dataList.value = result
            }

            override fun onFailure(message: String) {
            }

            override fun onFinally() {
            }

        })
    }
}