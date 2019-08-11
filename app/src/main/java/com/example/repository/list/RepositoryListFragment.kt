package com.example.repository.list

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.R
import com.example.main.TopFragment
import com.example.repository.RepositoryData
import kotlinx.android.synthetic.main.repository_list_fragment.*

class RepositoryListFragment : Fragment() {

    companion object {
        fun newInstance(userName: String) = RepositoryListFragment().apply {
            arguments = Bundle().apply {
                putString(TopFragment.KEY_USER_NAME, userName)
            }
        }
    }

    private lateinit var listViewModel: RepositoryListViewModel
    private val controller by lazy {
        RepositoryListController(object :
            RepositoryListController.OnClickListener {
            override fun onClick(data: RepositoryData) {
                Toast.makeText(context, "${data.full_name}", Toast.LENGTH_SHORT).show()
            }
        })
    }

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
