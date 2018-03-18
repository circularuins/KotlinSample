package com.circularuins.kotlinsample.domain.usecase

import com.circularuins.kotlinsample.domain.model.Article
import com.circularuins.kotlinsample.domain.model.TimeKeeper
import com.circularuins.kotlinsample.domain.repository.ArticlesRepository
import com.circularuins.kotlinsample.domain.repository.TimeRepository
import io.reactivex.Observable
import java.util.*

/**
 * Created by wake on 2018/02/21.
 */
class ArticlesViewUseCase(private val repository: ArticlesRepository,
                          private val timeRepository: TimeRepository) {

    fun getNews(): Observable<List<Article>> {

        val needCache = TimeKeeper(timeRepository.getTime()).needCache()

        return Observable.create { subscriber ->
            repository
                    .getNews(needCache)
                    .subscribe({
                        subscriber.onNext(it)
                        subscriber.onComplete()

                        // 通信経由で取得した時刻を保存
                        if (!needCache) {
                            timeRepository.setTime(Date())
                        }
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
                        subscriber.onComplete()
                    }, {
                        subscriber.onError(it)
                    })
        }
    }
}