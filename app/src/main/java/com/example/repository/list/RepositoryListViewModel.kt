package com.example.repository.list

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.repository.RepositoryData
import com.example.repository.api.ApiManager

class RepositoryListViewModel : ViewModel() {
    val dataList = MutableLiveData<Array<RepositoryData>>()

    fun getDataFromGithubApi(userName: String) {
        ApiManager.getRepositoryFromUserName(userName, object: ApiManager.CallbackListener<Array<RepositoryData>> {
            override fun onSuccess(result: Array<RepositoryData>) {
                Log.d("デバッグ", "サクセス")
                dataList.value = result
            }

            override fun onFailure(message: String) {
                Log.d("デバッグ", message)
            }

            override fun onFinally() {
                Log.d("デバッグ", "ファイナリー")
            }

        })
    }
}