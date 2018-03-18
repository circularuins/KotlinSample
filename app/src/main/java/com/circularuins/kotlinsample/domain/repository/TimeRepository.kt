package com.circularuins.kotlinsample.domain.repository

import java.util.*

/**
 * Created by circularuins on 2018/03/18.
 */
interface TimeRepository {

    fun getTime(): Date?

    fun setTime(time: Date)
}