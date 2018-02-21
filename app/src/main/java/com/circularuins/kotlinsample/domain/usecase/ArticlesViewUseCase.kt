package com.circularuins.kotlinsample.domain.usecase

import com.circularuins.kotlinsample.domain.model.Article
import com.circularuins.kotlinsample.domain.repository.ArticlesRepository
import com.trello.rxlifecycle2.LifecycleTransformer
import io.reactivex.Observer
import io.reactivex.disposables.Disposable

/**
 * Created by wake on 2018/02/21.
 */
class ArticlesViewUseCase(private val repository: ArticlesRepository,
                          private val transformer: LifecycleTransformer<List<Article>>) {

    fun getNews(observer: Observer<List<Article>>) {
        repository
                .getNews()
                .compose(transformer)
                .subscribe(createArticlesObserver(observer))
    }

    private fun createArticlesObserver(observer: Observer<List<Article>>): Observer<List<Article>> {
        return object : Observer<List<Article>> {
            override fun onComplete() {
                observer.onComplete()
            }

            override fun onSubscribe(d: Disposable) {
                observer.onSubscribe(d)
            }

            override fun onNext(t: List<Article>) {
                observer.onNext(t)
            }

            override fun onError(e: Throwable) {
                observer.onError(e)
            }
        }
    }
}