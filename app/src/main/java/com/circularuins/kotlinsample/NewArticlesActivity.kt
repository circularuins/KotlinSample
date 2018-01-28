package com.circularuins.kotlinsample

import android.os.Bundle
import android.view.View
import android.widget.ListView
import android.widget.ProgressBar
import com.circularuins.kotlinsample.client.QiitaClient
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity
import com.trello.rxlifecycle2.kotlin.bindToLifecycle
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class NewArticlesActivity : RxAppCompatActivity() {

    @Inject
    lateinit var qiitaClient: QiitaClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (application as KotlinSampleApp).component.inject(this)
        setContentView(R.layout.activity_articles_list)

        val listView: ListView = findViewById(R.id.list_view)
        val progressBar: ProgressBar = findViewById(R.id.progress_bar)

        val listAdapter = ArticleListAdapter(applicationContext)
        listView.adapter = listAdapter
        listView.setOnItemClickListener { adapterView, view, position, id ->
            val user = listAdapter.articles[position].user
            UsersArticlesActivity.intent(this, user).let { startActivity(it) }
        }

        progressBar.visibility = View.VISIBLE

        qiitaClient.getNews()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
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
