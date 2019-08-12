package com.example.repository.list.view

import android.view.View
import com.airbnb.epoxy.TypedEpoxyController
import com.example.LayoutRepositoryRowBindingModel_
import com.example.repository.list.model.data.RepositoryData

class RepositoryListController(private val listener: OnClickListener): TypedEpoxyController<Array<RepositoryData>>() {

    interface OnClickListener {
        fun onClick(view: View, data: RepositoryData)
    }

    override fun buildModels(dataList: Array<RepositoryData>) {
        dataList.forEachIndexed { index, data ->
            LayoutRepositoryRowBindingModel_()
                .id(data.id)
                .data(data)
                .isDark(index % 2 == 0)
                .clickListener(View.OnClickListener {
                    listener.onClick(it, data)
                })
                .addTo(this)
        }
    }
}