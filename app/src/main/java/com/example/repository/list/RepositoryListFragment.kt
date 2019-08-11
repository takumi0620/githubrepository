package com.example.repository.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.NetworkUtil
import com.example.R
import com.example.main.TopFragment
import com.example.repository.RepositoryData
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.repository_list_fragment.*

class RepositoryListFragment : Fragment() {

    companion object {
        const val KEY_HTML_URL = "KEY_HTML_URL"
        fun newInstance(userName: String) = RepositoryListFragment().apply {
            arguments = Bundle().apply {
                putString(TopFragment.KEY_USER_NAME, userName)
            }
        }
    }

    private lateinit var listViewModel: RepositoryListViewModel
    private val controller = RepositoryListController(object : RepositoryListController.OnClickListener {
        override fun onClick(view: View, data: RepositoryData) {
            val context = context ?: throw RuntimeException("context is null")

            if (NetworkUtil.isOnline(context)) {
                // online
                RepositoryReadmeDialogFragment.newInstance(data.html_url).showNow(fragmentManager, "${data.id}")
            } else {
                // offline
                Snackbar.make(view, "OFFLINE", Snackbar.LENGTH_SHORT).show()
            }
        }
    })

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,  savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.repository_list_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        listViewModel = ViewModelProviders.of(this).get(RepositoryListViewModel::class.java)

        val arguments = arguments ?: throw RuntimeException("No arguments")

        setUpView()
        setUpObserver()
        val userName = arguments.getString(TopFragment.KEY_USER_NAME) ?: throw RuntimeException("user name is null")
        listViewModel.getDataFromGithubApi(userName)
    }

    private fun setUpView() {
        repositoryList.adapter = controller.adapter
        repositoryList.layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
        repositoryList.addItemDecoration(DividerItemDecoration(activity, DividerItemDecoration.VERTICAL))
    }

    private fun setUpObserver() {
        listViewModel.dataList.observe(this, Observer {
            val dataList = it ?: throw RuntimeException("exception data is null")
            controller.setData(dataList)
        })
    }

}
