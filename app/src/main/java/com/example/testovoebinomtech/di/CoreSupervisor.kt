package com.example.testovoebinomtech.di

import android.content.Context

class CoreSupervisor(private val core: CoreInterface) {

    private val context: Context
        get() = core.context()

    private val controllersModule : ControllersModule
        get() = core.controllerModule()

    fun initArchitecture()
    {
        controllersModule.provideMainScreenController(context)
    }

    fun getApplicationControllers(): ControllersModule = core.controllerModule()
}