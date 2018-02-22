package com.circularuins.kotlinsample.app.presenter

import com.circularuins.kotlinsample.app.contract.NewArticlesContract
import com.circularuins.kotlinsample.domain.model.Article
import com.circularuins.kotlinsample.domain.model.User
import com.circularuins.kotlinsample.domain.repository.ArticlesRepository
import com.circularuins.kotlinsample.domain.usecase.ArticlesViewUseCase
import com.trello.rxlifecycle2.LifecycleTransformer
import io.reactivex.Observer
import io.reactivex.disposables.Disposable

/**
 * Created by circularuins on 2018/02/18.
 */
class NewArticlesPresenter(private val view: NewArticlesContract.View,
                           private val articlesRepository: ArticlesRepository,
                           private val transformer: LifecycleTransformer<List<Article>>)
    : NewArticlesContract.Presenter {

    override fun start() {
        view.setListTap()

        view.showProgress()

        val useCase = ArticlesViewUseCase(articlesRepository)
        useCase.getNews()
                .compose(transformer)
                .subscribe(object  : Observer<List<Article>> {
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
                })
    }

    override fun onListTap(user: User) {
        view.moveToUsersArticles(user)
    }
}