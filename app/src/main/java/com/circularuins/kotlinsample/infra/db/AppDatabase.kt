package com.circularuins.kotlinsample.infra.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.circularuins.kotlinsample.domain.model.Article
import com.circularuins.kotlinsample.infra.dao.ArticleDao

/**
 * Created by circularuins on 2018/02/25.
 */
@Database(entities = [Article::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    // getter
    abstract fun articleDao() : ArticleDao
}