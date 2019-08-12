package com.example.main.view

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.R
import com.example.repository.list.view.RepositoryListActivity
import kotlinx.android.synthetic.main.fragment_top.*

class TopFragment : Fragment() {
    companion object {
        const val KEY_USER_NAME = "KEY_USER_NAME"

        @JvmStatic
        fun newInstance() = TopFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_top, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpView()
    }

    private fun setUpView() {
        get.setOnClickListener {
            val userName = userName.text.toString()

            if (userName.isNullOrEmpty()) {
                Toast.makeText(context, "user name is empty...", Toast.LENGTH_SHORT).show()
            } else {
                val aIntent = Intent(context, RepositoryListActivity::class.java)
                aIntent.putExtra(KEY_USER_NAME, userName)
                startActivity(aIntent)
            }
        }
    }

}
