package com.circularuins.kotlinsample.client

import com.circularuins.kotlinsample.model.Article
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by circularuins on 2018/01/11.
 */
interface ArticleClient {

    @GET("/api/v2/items")
    fun search(@Query("query") query: String): Observable<List<Article>>
}