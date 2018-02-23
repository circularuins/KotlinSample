package com.circularuins.kotlinsample.domain.usecase

import com.circularuins.kotlinsample.domain.model.Article
import com.circularuins.kotlinsample.domain.repository.ArticlesRepository
import io.reactivex.Observable

/**
 * Created by wake on 2018/02/21.
 */
class ArticlesViewUseCase(private val repository: ArticlesRepository) {

    fun getNews(): Observable<List<Article>> {
        return Observable.create { subscriber ->
            repository
                    .getNews()
                    .subscribe({
                        // describe side effects here if need.
                        // data conversion if need.

                        subscriber.onNext(it)
                    }, {
                        subscriber.onError(it)
                    })
        }
    }

    fun getArticles(queryId: String): Observable<List<Article>> {
        return Observable.create { subscriber ->
            repository
                    .getArticles(queryId)
                    .subscribe({
                        // describe side effects here if need.
                        // data conversion if need.

                        subscriber.onNext(it)
                    }, {
                        subscriber.onError(it)
                    })
        }
    }
}