package com.example.repository.list

import android.view.View
import com.airbnb.epoxy.TypedEpoxyController
import com.example.LayoutRepositoryRowBindingModel_
import com.example.repository.RepositoryData

class RepositoryListController(private val listener: OnClickListener): TypedEpoxyController<Array<RepositoryData>>() {

    interface OnClickListener {
        fun onClick(data: RepositoryData)
    }

    override fun buildModels(dataList: Array<RepositoryData>) {
        dataList.forEach { data ->
            LayoutRepositoryRowBindingModel_()
                .id(data.id)
                .name(data.name)
                .clickListener(View.OnClickListener {
                    listener.onClick(data)
                })
                .addTo(this)
        }
    }
}