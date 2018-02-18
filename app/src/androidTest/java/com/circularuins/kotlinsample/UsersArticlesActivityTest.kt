package com.circularuins.kotlinsample

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.action.ViewActions.typeText
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.isDisplayed
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import android.view.View
import com.circularuins.kotlinsample.app.activity.UsersArticlesActivity
import com.circularuins.kotlinsample.infra.rest.QiitaClient
import io.reactivex.Observable
import org.hamcrest.Matcher
import org.hamcrest.Matchers.not
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.*


/**
 * Created by circularuins on 2018/01/20.
 */
@RunWith(AndroidJUnit4::class)
class UsersArticlesActivityTest {

    @JvmField
    @Rule
    val activityTestRule = ActivityTestRule(UsersArticlesActivity::class.java)

    @Test
    fun 各ビューが表示されていること_ただしプログレスバーは非表示() {
        onView(withId(R.id.list_view)).check(matches(isDisplayed()))
        onView(withId(R.id.query_edit_text)).check(matches(isDisplayed()))
        onView(withId(R.id.search_button)).check(matches(isDisplayed()))

        onView(withId(R.id.progress_bar)).check(matches(isNotDisplayed()))
    }

    fun isNotDisplayed(): Matcher<View> = not(isDisplayed())

    @Test
    fun `検索ボタンがタップされたら、入力されたクエリ文字列で記事検索APIを叩くこと`() {
        val articleClient = mock(QiitaClient::class.java).apply {
            `when`(search("user:circularuins")).thenReturn(Observable.just(listOf()))
        }
        activityTestRule.activity.qiitaClient = articleClient

        onView(withId(R.id.query_edit_text)).perform(typeText("user:circularuins"))
        onView(withId(R.id.search_button)).perform(click())

        verify(articleClient).search("user:circularuins")
    }
}