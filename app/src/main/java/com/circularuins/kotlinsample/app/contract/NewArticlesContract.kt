package com.circularuins.kotlinsample.app.contract

import com.circularuins.kotlinsample.domain.model.Article

/**
 * Created by circularuins on 2018/02/18.
 */
interface NewArticlesContract {

    interface View {
        fun showProgress()
        fun hideProgress()
        fun setList(articles: List<Article>)
        fun showError(error: Throwable)
        fun setListTap()
    }

    interface Presenter {
        fun start()
    }
}