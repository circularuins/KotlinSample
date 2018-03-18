package com.circularuins.kotlinsample.infra.repository

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.circularuins.kotlinsample.domain.repository.TimeRepository
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by circularuins on 2018/03/18.
 */
class TimeRepositoryImpl(context: Context) : TimeRepository {

    companion object {
        private const val PREF_KEY_TIME_RECORD = "time_record"
    }

    private val pref: SharedPreferences =
            PreferenceManager.getDefaultSharedPreferences(context.applicationContext)

    private val dateFormat: SimpleDateFormat
        get() {
            val format = SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.US)
            format.timeZone = TimeZone.getTimeZone("Asia/Tokyo")
            return format
        }

    override fun getTime(): Date? {
        val strDate = pref.getString(PREF_KEY_TIME_RECORD, null)
        return strDate?.let { dateFormat.parse(strDate) }
    }

    override fun setTime(time: Date) {
        pref.edit().putString(
                PREF_KEY_TIME_RECORD,
                dateFormat.format(time).toString()).apply()
    }
}