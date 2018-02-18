package com.circularuins.kotlinsample.app.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.ProgressBar
import com.circularuins.kotlinsample.app.adapter.ArticleListAdapter
import com.circularuins.kotlinsample.KotlinSampleApp
import com.circularuins.kotlinsample.R
import com.circularuins.kotlinsample.domain.model.User
import com.circularuins.kotlinsample.domain.repository.ArticlesRepository
import com.circularuins.kotlinsample.toast
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity
import com.trello.rxlifecycle2.kotlin.bindToLifecycle
import javax.inject.Inject

class UsersArticlesActivity : RxAppCompatActivity() {

    @Inject
    lateinit var articlesRepository: ArticlesRepository

    companion object {

        private const val ARTICLE_LIST_EXTRA: String = "article_list"

        fun intent(context: Context, user: User): Intent =
                Intent(context, UsersArticlesActivity::class.java).putExtra(ARTICLE_LIST_EXTRA, user)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (application as KotlinSampleApp).component.inject(this)
        setContentView(R.layout.activity_users_articles)

        val listView: ListView = findViewById(R.id.list_view)
        val queryEditText: EditText = findViewById(R.id.query_edit_text)
        val searchButton: Button = findViewById(R.id.search_button)
        val progressBar: ProgressBar = findViewById(R.id.progress_bar)

        val listAdapter = ArticleListAdapter(applicationContext)
        listView.adapter = listAdapter
        listView.setOnItemClickListener { adapterView, view, position, id ->
            val article = listAdapter.articles[position]
            ArticleActivity.intent(this, article).let { startActivity(it) }
        }

        searchButton.setOnClickListener {
            progressBar.visibility = View.VISIBLE

            val queryId: String = "user:" + queryEditText.text.toString()
            articlesRepository.getArticles(queryId)
                    .doAfterTerminate {
                        progressBar.visibility = View.GONE
                    }
                    .bindToLifecycle(this)
                    .subscribe({
                        queryEditText.text.clear()
                        listAdapter.articles = it
                        listAdapter.notifyDataSetChanged()
                    }, {
                        toast("エラー : $it")
                    })
        }

        val user: User = intent.getParcelableExtra(ARTICLE_LIST_EXTRA)
        val queryId: String = "user:" + user.id
        progressBar.visibility = View.VISIBLE
        articlesRepository.getArticles(queryId)
                .doAfterTerminate {
                    progressBar.visibility = View.GONE
                }
                .bindToLifecycle(this)
                .subscribe({
                    listAdapter.articles = it
                    listAdapter.notifyDataSetChanged()
                }, {
                    toast("エラー : $it")
                })
    }
}
