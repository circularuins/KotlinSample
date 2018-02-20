package com.circularuins.kotlinsample.app.presenter

import com.circularuins.kotlinsample.app.contract.UsersArticlesContract
import com.circularuins.kotlinsample.domain.model.Article
import com.circularuins.kotlinsample.domain.repository.ArticlesRepository
import com.trello.rxlifecycle2.LifecycleTransformer

/**
 * Created by circularuins on 2018/02/18.
 */
class UsersArticlesPresenter(private val view: UsersArticlesContract.View,
                             private val articlesRepository: ArticlesRepository,
                             private val transformer: LifecycleTransformer<List<Article>>,
                             private val queryId: String)
    : UsersArticlesContract.Presenter {

    override fun start() {
        view.setListTap()

        view.showProgress()

        articlesRepository.getArticles(queryId)
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
}