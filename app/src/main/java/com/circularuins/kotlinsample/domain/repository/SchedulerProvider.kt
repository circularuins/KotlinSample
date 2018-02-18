package com.circularuins.kotlinsample.domain.repository

import io.reactivex.Scheduler

/**
 * Created by circularuins on 2018/02/17.
 */
interface SchedulerProvider {

    fun ui(): Scheduler

    fun computation(): Scheduler

    fun trampoline(): Scheduler

    fun newThread(): Scheduler

    fun io(): Scheduler
}