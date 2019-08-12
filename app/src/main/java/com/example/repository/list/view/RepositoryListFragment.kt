package com.example.repository.list.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.R
import com.example.databinding.RepositoryListFragmentBindingImpl
import com.example.main.view.TopFragment
import com.example.repository.list.model.data.RepositoryData
import com.example.repository.list.viewmodel.RepositoryListViewModel
import com.example.util.NetworkUtil
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.repository_list_fragment.*
import org.koin.android.viewmodel.ext.android.viewModel

class RepositoryListFragment : Fragment() {

    companion object {
        const val KEY_HTML_URL = "KEY_HTML_URL"
        fun newInstance(userName: String) = RepositoryListFragment().apply {
            arguments = Bundle().apply {
                putString(TopFragment.KEY_USER_NAME, userName)
            }
        }
    }

    private val listViewModel by viewModel<RepositoryListViewModel>()

    private val controller = RepositoryListController(object :
        RepositoryListController.OnClickListener {
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
        val binding = DataBindingUtil.inflate<RepositoryListFragmentBindingImpl>(inflater, R.layout.repository_list_fragment, container, false)
        binding.lifecycleOwner = this
        binding.viewModel = listViewModel
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

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
        back.setOnClickListener {
            activity?.onBackPressed()
        }
    }

    private fun setUpObserver() {
        listViewModel.dataList.observe(this, Observer { dataList ->
            if (dataList.isNullOrEmpty()) {
                repositoryList.visibility = View.GONE
                noDataText.visibility = View.VISIBLE
                back.visibility = View.VISIBLE
            } else {
                controller.setData(dataList)
            }
        })
    }

}
