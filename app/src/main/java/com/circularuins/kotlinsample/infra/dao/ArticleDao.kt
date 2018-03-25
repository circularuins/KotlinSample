package com.circularuins.kotlinsample.infra.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.circularuins.kotlinsample.domain.model.Article
import io.reactivex.Flowable

/**
 * Created by circularuins on 2018/02/24.
 */
@Dao
interface ArticleDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(articles: List<Article>)

    @Query("SELECT * FROM Article ORDER BY created_at DESC LIMIT :limit")
    fun getNews(limit: Int): Flowable<List<Article>>
}