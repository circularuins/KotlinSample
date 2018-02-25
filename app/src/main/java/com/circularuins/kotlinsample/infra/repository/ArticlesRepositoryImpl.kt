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

    override fun getNews(): Flowable<List<Article>> {
        // TODO キャッシュの閾値で分岐させる感じ
        /*return client.getNews()
                .subscribeOn(subScribe)
                .observeOn(observe)*/
        val dao = KotlinSampleApp.database.articleDao()
        return dao.findAll()
    }

    override fun getArticles(query: String): Observable<List<Article>> = client.search(query)
            .subscribeOn(subScribe)
            .observeOn(observe)
}