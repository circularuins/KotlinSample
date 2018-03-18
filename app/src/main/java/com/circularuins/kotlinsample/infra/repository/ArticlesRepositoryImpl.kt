package com.circularuins.kotlinsample.infra.repository

import com.circularuins.kotlinsample.KotlinSampleApp
import com.circularuins.kotlinsample.domain.model.Article
import com.circularuins.kotlinsample.domain.repository.ArticlesRepository
import com.circularuins.kotlinsample.infra.rest.QiitaClient
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Scheduler

/**
 * Created by circularuins on 2018/02/17.
 */
class ArticlesRepositoryImpl(private val client: QiitaClient,
                             private val subScribe: Scheduler,
                             private val observe: Scheduler) : ArticlesRepository {

    override fun getNews(needCache: Boolean): Flowable<List<Article>> = when (needCache) {
        // 通信経由で取得
        false -> client.getNews()
                // キャッシュ保存だが、ストリームの途中で副作用して良いのか悩む。。
                .doOnNext { it ->
                    KotlinSampleApp.database.articleDao().insertAll(it) }
                .subscribeOn(subScribe)
                .observeOn(observe)
        // キャッシュを取得
        true -> KotlinSampleApp.database.articleDao().findAll()
    }

    override fun getArticles(query: String): Observable<List<Article>> = client.search(query)
            .subscribeOn(subScribe)
            .observeOn(observe)
}