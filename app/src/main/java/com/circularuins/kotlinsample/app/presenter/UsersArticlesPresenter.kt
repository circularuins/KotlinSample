package com.circularuins.kotlinsample.app.presenter

import com.circularuins.kotlinsample.app.contract.UsersArticlesContract
import com.circularuins.kotlinsample.domain.model.Article
import com.circularuins.kotlinsample.domain.repository.ArticlesRepository
import com.circularuins.kotlinsample.domain.usecase.ArticlesViewUseCase
import com.trello.rxlifecycle2.LifecycleTransformer
import io.reactivex.Observer
import io.reactivex.disposables.Disposable

/**
 * Created by circularuins on 2018/02/18.
 */
class UsersArticlesPresenter(private val view: UsersArticlesContract.View,
                             private val articlesRepository: ArticlesRepository,
                             private val transformer: LifecycleTransformer<List<Article>>,
                             private val queryId: String)
    : UsersArticlesContract.Presenter {

    private val articlesObserver: Observer<List<Article>>
        get() = object  : Observer<List<Article>> {
            override fun onComplete() {
                view.hideProgress()
            }

            override fun onSubscribe(d: Disposable) {
                // NOP
            }

            override fun onNext(t: List<Article>) {
                view.setList(t)
            }

            override fun onError(e: Throwable) {
                view.showError(e)
            }
        }

    override fun start() {
        view.setListTap()

        view.showProgress()

        val useCase = ArticlesViewUseCase(articlesRepository, transformer)
        useCase.getArticles(queryId, articlesObserver)
    }

    override fun onListTap(article: Article) {
        view.moveArticle(article)
    }
}