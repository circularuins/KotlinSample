package com.circularuins.kotlinsample.domain.repository

import com.circularuins.kotlinsample.domain.model.Article
import io.reactivex.Observable

/**
 * Created by circularuins on 2018/02/17.
 */
interface ArticlesRepository {

    fun getNews(): Observable<List<Article>>

    fun getArticles(query: String): Observable<List<Article>>
}