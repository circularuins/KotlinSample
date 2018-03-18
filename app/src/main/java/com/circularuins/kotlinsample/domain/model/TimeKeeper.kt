package com.circularuins.kotlinsample.domain.model

import java.util.*

/**
 * Created by circularuins on 2018/03/18.
 */
class TimeKeeper(private val targetDate: Date?) {

    companion object {
        private const val CACHE_INTERVAL = 10
    }
    private val nowDate: Date = Date()
    private val calender = Calendar.getInstance()

    fun needCache(): Boolean {
        calender.time = nowDate
        calender.add(Calendar.MINUTE, -1 * CACHE_INTERVAL)

        // 比較対象時刻が現在から10分以内ならtrue
        return targetDate?.compareTo(calender.time) == 1
    }
}