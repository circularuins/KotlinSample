package com.circularuins.kotlinsample.app.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ListView
import android.widget.ProgressBar
import com.circularuins.kotlinsample.KotlinSampleApp
import com.circularuins.kotlinsample.R
import com.circularuins.kotlinsample.app.adapter.ArticleListAdapter
import com.circularuins.kotlinsample.app.contract.UsersArticlesContract
import com.circularuins.kotlinsample.app.presenter.UsersArticlesPresenter
import com.circularuins.kotlinsample.bindVew
import com.circularuins.kotlinsample.domain.model.Article
import com.circularuins.kotlinsample.domain.model.User
import com.circularuins.kotlinsample.domain.repository.ArticlesRepository
import com.circularuins.kotlinsample.toast
import com.trello.rxlifecycle2.android.ActivityEvent
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity
import javax.inject.Inject

class UsersArticlesActivity : RxAppCompatActivity(), UsersArticlesContract.View {

    @Inject
    lateinit var articlesRepository: ArticlesRepository

    private val progressBar: ProgressBar by bindVew(R.id.progress_bar)
    private val listView: ListView by bindVew(R.id.list_view)

    private val listAdapter: ArticleListAdapter by lazy {
        ArticleListAdapter(applicationContext)
    }

    // TODO lazyまたはdaggerでどうにかしたい
    private var presenter: UsersArticlesContract.Presenter? = null

    companion object {

        private const val ARTICLE_LIST_EXTRA: String = "article_list"

        fun intent(context: Context, user: User): Intent =
                Intent(context, UsersArticlesActivity::class.java).putExtra(ARTICLE_LIST_EXTRA, user)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (application as KotlinSampleApp).component.inject(this)
        setContentView(R.layout.activity_users_articles)

        val user: User = intent.getParcelableExtra(ARTICLE_LIST_EXTRA)
        val queryId: String = "user:" + user.userId
        presenter = UsersArticlesPresenter(this, articlesRepository,
                bindUntilEvent(ActivityEvent.DESTROY), queryId)
        presenter?.start()
    }

    override fun showProgress() {
        progressBar.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        progressBar.visibility = View.GONE
    }

    override fun setList(articles: List<Article>) {
        listAdapter.articles = articles
        listAdapter.notifyDataSetChanged()
    }

    override fun showError(error: Throwable) {
        toast("エラー : $error")
    }

    override fun setListTap() {
        listView.adapter = listAdapter
        listView.setOnItemClickListener { adapterView, view, position, id ->
            presenter?.onListTap(listAdapter.articles[position])
        }
    }

    override fun moveArticle(article: Article) {
        ArticleActivity.intent(this, article).let { startActivity(it) }
    }
}
