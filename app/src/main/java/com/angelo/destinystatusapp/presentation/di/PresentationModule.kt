package com.angelo.destinystatusapp.presentation.di

import com.angelo.destinystatusapp.presentation.viewmodel.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val presentationModule = module {
    viewModel { MainViewModel() }
}
