package com.circularuins.kotlinsample.dagger

import com.circularuins.kotlinsample.MainActivity
import dagger.Component
import javax.inject.Singleton

/**
 * Created by circularuins on 2018/01/21.
 */
@Component(modules = arrayOf(ClientModule::class))
@Singleton
interface AppComponent {

    fun inject(mainActivity: MainActivity)
}