package com.circularuins.kotlinsample.app.contract

import com.circularuins.kotlinsample.domain.model.Article

/**
 * Created by circularuins on 2018/02/20.
 */
interface UsersArticlesContract {

    interface View {
        fun showProgress()
        fun hideProgress()
        fun setList(articles: List<Article>)
        fun showError(error: Throwable)
        fun setListTap()
        fun moveArticle(article: Article)
    }

    interface Presenter {
        fun start()
        fun onListTap(article: Article)
    }
}