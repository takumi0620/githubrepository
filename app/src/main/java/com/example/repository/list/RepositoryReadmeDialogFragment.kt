package com.example.repository.list

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.example.R
import kotlinx.android.synthetic.main.fragment_repository_readme_dialog.view.*

class RepositoryReadmeDialogFragment : DialogFragment() {

    companion object {
        @JvmStatic
        fun newInstance(htmlUrl: String) =
            RepositoryReadmeDialogFragment().apply {
                arguments = Bundle().apply {
                    putString(RepositoryListFragment.KEY_HTML_URL, htmlUrl)
                }
            }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val activity = activity ?: throw RuntimeException("activity is null")
        val arguments = arguments ?: throw RuntimeException("arguments is null")
        val htmlUrl = arguments.getString(RepositoryListFragment.KEY_HTML_URL)
        val readmeDialog = AlertDialog.Builder(activity)
        val view = activity.layoutInflater.inflate(R.layout.fragment_repository_readme_dialog, null)
        view.readMeBrowser.loadUrl("$htmlUrl/blob/master/README.md")
        readmeDialog.setView(view)
        isCancelable = false
        view.close.setOnClickListener {
            dismiss()
        }
        return readmeDialog.create()
    }
}
