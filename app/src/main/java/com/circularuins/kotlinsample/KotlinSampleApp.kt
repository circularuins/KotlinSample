package com.circularuins.kotlinsample

import android.app.Application
import android.arch.persistence.room.Room
import com.circularuins.kotlinsample.di.AppComponent
import com.circularuins.kotlinsample.di.DaggerAppComponent
import com.circularuins.kotlinsample.infra.db.AppDatabase

/**
 * Created by circularuins on 2018/01/21.
 */
class KotlinSampleApp : Application() {

    val component: AppComponent by lazy {
        DaggerAppComponent.create()
    }

    companion object {
        lateinit var database: AppDatabase
    }

    override fun onCreate() {
        super.onCreate()

        // TODO DaggerでSingltonにする
        database = Room.databaseBuilder(
                this, AppDatabase::class.java, "qiita_client.db").build()
    }
}