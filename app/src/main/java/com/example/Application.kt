package com.example

import android.app.Application
import com.example.repository.list.viewmodel.RepositoryListViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.dsl.module

class Application: Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@Application)
            modules(module)
        }
    }

    private val module: Module = module {
        viewModel { RepositoryListViewModel(get())}
    }
}