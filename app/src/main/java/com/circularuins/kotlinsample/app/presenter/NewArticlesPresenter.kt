package com.circularuins.kotlinsample.app.presenter

import com.circularuins.kotlinsample.app.contract.NewArticlesContract
import com.circularuins.kotlinsample.domain.model.Article
import com.circularuins.kotlinsample.domain.model.User
import com.circularuins.kotlinsample.domain.repository.ArticlesRepository
import com.circularuins.kotlinsample.domain.repository.TimeRepository
import com.circularuins.kotlinsample.domain.usecase.ArticlesViewUseCase
import com.trello.rxlifecycle2.LifecycleTransformer

/**
 * Created by circularuins on 2018/02/18.
 */
class NewArticlesPresenter(private val view: NewArticlesContract.View,
                           private val articlesRepository: ArticlesRepository,
                           private val timeRepository: TimeRepository,
                           private val transformer: LifecycleTransformer<List<Article>>)
    : NewArticlesContract.Presenter {

    override fun start() {
        view.setListTap()

        view.showProgress()

        val useCase = ArticlesViewUseCase(articlesRepository, timeRepository)
        useCase.getNews()
                .doAfterTerminate {
                    view.hideProgress()
                }
                .compose(transformer)
                .subscribe({
                    view.setList(it)
                }, {
                    view.showError(it)
                })
    }

    override fun onListTap(user: User) {
        view.moveToUsersArticles(user)
    }
}