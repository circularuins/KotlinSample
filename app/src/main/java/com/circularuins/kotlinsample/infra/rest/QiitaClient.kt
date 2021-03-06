package com.circularuins.kotlinsample.infra.rest

import com.circularuins.kotlinsample.domain.model.Article
import io.reactivex.Flowable
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by circularuins on 2018/01/11.
 */
interface QiitaClient {

    @GET("/api/v2/items")
    fun search(@Query("query") query: String,
               @Query("per_page") perPage: Int): Observable<List<Article>>

    @GET("/api/v2/items")
    fun getNews(@Query("per_page") perPage: Int): Flowable<List<Article>>
}