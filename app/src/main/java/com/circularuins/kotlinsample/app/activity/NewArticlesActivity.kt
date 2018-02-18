package com.circularuins.kotlinsample.app.activity

import android.os.Bundle
import android.view.View
import android.widget.ListView
import android.widget.ProgressBar
import com.circularuins.kotlinsample.KotlinSampleApp
import com.circularuins.kotlinsample.R
import com.circularuins.kotlinsample.app.adapter.ArticleListAdapter
import com.circularuins.kotlinsample.app.contract.NewArticlesContract
import com.circularuins.kotlinsample.app.presenter.NewArticlesPresenter
import com.circularuins.kotlinsample.bindVew
import com.circularuins.kotlinsample.domain.model.Article
import com.circularuins.kotlinsample.domain.repository.ArticlesRepository
import com.circularuins.kotlinsample.toast
import com.trello.rxlifecycle2.android.ActivityEvent
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity
import javax.inject.Inject

class NewArticlesActivity : RxAppCompatActivity(), NewArticlesContract.View {

    @Inject
    lateinit var articlesRepository: ArticlesRepository

    private val progressBar: ProgressBar by bindVew(R.id.progress_bar)
    private val listView: ListView by bindVew(R.id.list_view)

    private val listAdapter: ArticleListAdapter by lazy {
        ArticleListAdapter(applicationContext)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (application as KotlinSampleApp).component.inject(this)
        setContentView(R.layout.activity_articles_list)

        val presenter: NewArticlesContract.Presenter =
                NewArticlesPresenter(this, articlesRepository, bindUntilEvent(ActivityEvent.DESTROY))
        presenter.start()
    }

    override fun showProgress() {
        progressBar.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        progressBar.visibility = View.GONE
    }

    override fun setListTap() {
        listView.adapter = listAdapter
        listView.setOnItemClickListener { adapterView, view, position, id ->
            val user = listAdapter.articles[position].user
            UsersArticlesActivity.intent(this, user).let { startActivity(it) }
        }
    }

    override fun setList(articles: List<Article>) {
        listAdapter.articles = articles
        listAdapter.notifyDataSetChanged()
    }

    override fun showError(error: Throwable) {
        toast("エラー : $error")
    }
}
