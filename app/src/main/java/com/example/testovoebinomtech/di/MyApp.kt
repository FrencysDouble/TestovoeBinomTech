package com.example.testovoebinomtech.di

import android.app.Application
import com.github.klee0kai.stone.Stone

class MyApp: Application() {

    private lateinit var coreInterface: CoreInterface

    val cs : CoreSupervisor
        get() = coreInterface.coreSuper()

    override fun onCreate() {
        super.onCreate()

        coreInterface = Stone.createComponent(CoreInterface::class.java)
        coreInterface.context(this)

        coreInterface.coreSuper(CoreSupervisor(coreInterface))

        cs.initArchitecture()
    }
}