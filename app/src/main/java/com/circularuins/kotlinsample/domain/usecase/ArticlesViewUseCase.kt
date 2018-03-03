package com.circularuins.kotlinsample.domain.usecase

import com.circularuins.kotlinsample.KotlinSampleApp
import com.circularuins.kotlinsample.domain.model.Article
import com.circularuins.kotlinsample.domain.repository.ArticlesRepository
import io.reactivex.Observable
import kotlin.concurrent.thread

/**
 * Created by wake on 2018/02/21.
 */
class ArticlesViewUseCase(private val repository: ArticlesRepository) {

    fun getNews(): Observable<List<Article>> {
        return Observable.create { subscriber ->
            repository
                    .getNews(false) // TODO 10分経過でtrueとかのロジックを作成する
                    .subscribe({
                        // describe side effects here if need.
                        // data conversion if need.

                        // キャッシュの保存
                        //TODO Repositoryに移植してそっちを呼び出す感じ？？
                        thread {
                            val dao = KotlinSampleApp.database.articleDao()
                            dao.insertAll(it)
                        }

                        subscriber.onNext(it)
                        subscriber.onComplete()
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