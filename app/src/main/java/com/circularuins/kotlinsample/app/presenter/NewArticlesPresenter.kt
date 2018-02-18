package com.circularuins.kotlinsample.app.presenter

import com.circularuins.kotlinsample.app.contract.NewArticlesContract
import com.circularuins.kotlinsample.domain.model.Article
import com.circularuins.kotlinsample.domain.repository.ArticlesRepository
import com.trello.rxlifecycle2.LifecycleTransformer

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

        articlesRepository.getNews()
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