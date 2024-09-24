package com.example.testovoebinomtech.di

import android.content.Context
import com.example.testovoebinomtech.controllers.MainScreenController
import com.github.klee0kai.stone.annotations.module.Module
import com.github.klee0kai.stone.annotations.module.Provide

@Module
interface ControllersModule {

    @Provide(cache = Provide.CacheType.Soft)
    fun provideMainScreenController(context: Context? = null) : MainScreenController
}