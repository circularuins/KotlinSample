package com.circularuins.kotlinsample

import android.app.Application
import com.circularuins.kotlinsample.dagger.AppComponent
import com.circularuins.kotlinsample.dagger.DaggerAppComponent

/**
 * Created by circularuins on 2018/01/21.
 */
class KotlinSampleApp : Application() {

    val component: AppComponent by lazy {
        DaggerAppComponent.create()
    }
}