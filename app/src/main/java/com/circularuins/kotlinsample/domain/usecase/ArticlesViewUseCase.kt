package com.circularuins.kotlinsample.domain.usecase

import com.circularuins.kotlinsample.domain.model.Article
import com.circularuins.kotlinsample.domain.repository.ArticlesRepository
import io.reactivex.Observable

/**
 * Created by wake on 2018/02/21.
 */
class ArticlesViewUseCase(private val repository: ArticlesRepository) {

    fun getNews(): Observable<List<Article>> {
        return repository
                .getNews()
                .doOnNext { t: List<Article> ->
                    // NOP
                }
                .map { t -> t }
    }

    fun getArticles(queryId: String): Observable<List<Article>> {
        return repository
                .getArticles(queryId)
                .doOnNext { t: List<Article> ->
                    // NOP
                }
                .map { t -> t }
    }
}