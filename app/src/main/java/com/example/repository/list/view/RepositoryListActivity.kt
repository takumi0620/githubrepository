package com.example.repository.list.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.R
import com.example.main.view.TopFragment

class RepositoryListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.repository_list_activity)
        setUpView()
    }

    private fun setUpView() {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.repository,
            RepositoryListFragment.newInstance(
                intent.getStringExtra(
                    TopFragment.KEY_USER_NAME
                )
            )
        )
        transaction.commitNow()
    }

}
