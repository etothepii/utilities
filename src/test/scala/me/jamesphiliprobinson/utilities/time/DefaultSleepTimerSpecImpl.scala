package me.jamesphiliprobinson.utilities.time

import org.scalatest.{Matchers, FunSuite}

/**
  * Created by James Robinson on 03/04/2016.
  */
class DefaultSleepTimerSpecImpl extends FunSuite with Matchers {

  test("Can sleep for the time required following creation") {
    val timer = new DefaultSleepTimerImpl(100, 50)
    val time = System.currentTimeMillis
    timer.sleep
    val timePassed = System.currentTimeMillis - time
    timePassed should be >= 100L
    timePassed should be < 110L
  }

  test("Can sleep for the time required following use") {
    val timer = new DefaultSleepTimerImpl(100, 50)
    timer.sleep
    val time = System.currentTimeMillis
    timer.sleep
    val timePassed = System.currentTimeMillis - time
    timePassed should be >= 100L
    timePassed should be < 110L
  }

  test("Can sleep for the time min required following an over sleep after creation") {
    val timer = new DefaultSleepTimerImpl(100, 50)
    Thread sleep 150L
    val time = System.currentTimeMillis
    timer.sleep
    val timePassed = System.currentTimeMillis - time
    timePassed should be >= 50L
    timePassed should be < 60L
  }

  test("Can sleep for the time min required following an over sleep") {
    val timer = new DefaultSleepTimerImpl(100, 50)
    timer.sleep
    Thread sleep 150L
    val time = System.currentTimeMillis
    timer.sleep
    val timePassed = System.currentTimeMillis - time
    timePassed should be >= 50L
    timePassed should be < 60L
  }

  test("Can sleep for the time required following reset") {
    val timer = new DefaultSleepTimerImpl(100, 50)
    timer.sleep
    timer.sleep
    Thread sleep 150L
    timer.sleep
    Thread sleep 150L
    timer.reset
    val time = System.currentTimeMillis
    timer.sleep
    val timePassed = System.currentTimeMillis - time
    timePassed should be >= 100L
    timePassed should be < 110L
  }

}
