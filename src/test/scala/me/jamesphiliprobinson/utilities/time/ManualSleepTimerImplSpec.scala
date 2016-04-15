package me.jamesphiliprobinson.utilities.time

import org.scalatest.{FunSuite, Matchers}

/**
  * Created by James Robinson on 15/04/2016.
  */
class ManualSleepTimerImplSpec extends FunSuite with Matchers {

  test("Simple sleep timer does not hang when not enabled") {
    val sleepTimer = new ManualSleepTimerImpl
    val startTime = System.currentTimeMillis
    sleepTimer.sleep
    val time = System.currentTimeMillis - startTime
    time should be <= 1L
  }

  test("Simple sleep timer should hand when enabled") {
    val sleepTimer = new ManualSleepTimerImpl
    sleepTimer setShouldSleep true
    val startTime = System.currentTimeMillis
    new Thread(new Runnable {
      override def run() = {
        Thread sleep 100L
        sleepTimer setShouldSleep false
      }
    }).start
    sleepTimer.sleep
    val time = System.currentTimeMillis - startTime
    time should be >= 100L
    time should be <= 120L
  }

  test("Should auto restart timer if not told to do so") {
    val sleepTimer = new ManualSleepTimerImpl
    sleepTimer setShouldSleep true
    sleepTimer setAutoActivate true
    var startTime = System.currentTimeMillis
    new Thread(new Runnable {
      override def run() = {
        Thread sleep 100L
        sleepTimer setShouldSleep false
      }
    }).start
    sleepTimer.sleep
    var time = System.currentTimeMillis - startTime
    time should be >= 100L
    time should be <= 120L
    startTime = System.currentTimeMillis
    new Thread(new Runnable {
      override def run() = {
        Thread sleep 100L
        sleepTimer setShouldSleep false
      }
    }).start
    sleepTimer.sleep
    time = System.currentTimeMillis - startTime
    time should be >= 100L
    time should be <= 120L
  }

  test("Should auto restart timer if told to do so") {
    val sleepTimer = new ManualSleepTimerImpl
    sleepTimer setShouldSleep true
    sleepTimer setAutoActivate true
    new Thread(new Runnable {
      override def run() = {
        Thread sleep 100L
        sleepTimer setShouldSleep false
      }
    }).start
    sleepTimer.sleep
    val startTime = System.currentTimeMillis
    new Thread(new Runnable {
      override def run() = {
        Thread sleep 100L
        sleepTimer setShouldSleep false
      }
    }).start
    sleepTimer.sleep
    val time = System.currentTimeMillis - startTime
    time should be >= 100L
    time should be <= 120L
  }

  test("Should auto restart timer if told to do so even if not currently set") {
    val sleepTimer = new ManualSleepTimerImpl
    sleepTimer setAutoActivate true
    sleepTimer.sleep
    val startTime = System.currentTimeMillis
    new Thread(new Runnable {
      override def run() = {
        Thread sleep 100L
        sleepTimer setShouldSleep false
      }
    }).start
    sleepTimer.sleep
    val time = System.currentTimeMillis - startTime
    time should be >= 100L
    time should be <= 120L
  }

  test("Should not throw Interrupted Exception is interrupted during sleep") {
    val sleepTimer = new ManualSleepTimerImpl
    val thread = Thread.currentThread
    sleepTimer setAutoActivate true
    sleepTimer.sleep
    val startTime = System.currentTimeMillis
    new Thread(new Runnable {
      override def run() = {
        Thread sleep 10L
        thread.interrupt
      }
    }).start
    sleepTimer.sleep
    val time = System.currentTimeMillis - startTime
    time should be >= 10L
    time should be <= 30L
  }

  test("Should throw Interrupted Exception if interrupted during sleep on unprotected method call") {
    val sleepTimer = new ManualSleepTimerImpl
    val thread = Thread.currentThread
    sleepTimer setAutoActivate true
    sleepTimer.sleep
    new Thread(new Runnable {
      override def run() = {
        Thread sleep 10L
        thread.interrupt
      }
    }).start
    intercept[InterruptedException] {
      sleepTimer.sleepWithInterruptedException
    }
  }

  test("Reset should clear sleeping and auto restart") {
    val sleepTimer = new ManualSleepTimerImpl
    sleepTimer setAutoActivate true
    sleepTimer setShouldSleep true
    sleepTimer.reset
    val startTime = System.currentTimeMillis
    sleepTimer.sleep
    val time = System.currentTimeMillis - startTime
    time should be <= 2L
    sleepTimer.isAutoActivate shouldBe false
    sleepTimer.isShouldSleep shouldBe false
  }

  test("Reset should clear sleeping and auto restart 1") {
    val sleepTimer = new ManualSleepTimerImpl
    sleepTimer setShouldSleep true
    sleepTimer.reset
    val startTime = System.currentTimeMillis
    sleepTimer.sleep
    val time = System.currentTimeMillis - startTime
    time should be <= 2L
    sleepTimer.isAutoActivate shouldBe false
    sleepTimer.isShouldSleep shouldBe false
  }

  test("Reset should clear sleeping and auto restart 2") {
    val sleepTimer = new ManualSleepTimerImpl
    sleepTimer setAutoActivate true
    sleepTimer.reset
    val startTime = System.currentTimeMillis
    sleepTimer.sleep
    val time = System.currentTimeMillis - startTime
    time should be <= 2L
    sleepTimer.isAutoActivate shouldBe false
    sleepTimer.isShouldSleep shouldBe false
  }

  test("Reset should clear sleeping and auto restart 3") {
    val sleepTimer = new ManualSleepTimerImpl
    sleepTimer.reset
    val startTime = System.currentTimeMillis
    sleepTimer.sleep
    val time = System.currentTimeMillis - startTime
    time should be <= 2L
    sleepTimer.isAutoActivate shouldBe false
    sleepTimer.isShouldSleep shouldBe false
  }

  test("Reset should clear sleeping and auto restart of sleeping thread") {
    val sleepTimer = new ManualSleepTimerImpl
    sleepTimer setShouldSleep true
    val startTime = System.currentTimeMillis
    new Thread(new Runnable {
      override def run() = {
        Thread sleep 100L
        sleepTimer reset
      }
    }).start
    sleepTimer.sleep
    val time = System.currentTimeMillis - startTime
    time should be >= 100L
    time should be <= 120L
    sleepTimer.isAutoActivate shouldBe false
    sleepTimer.isShouldSleep shouldBe false
  }

  test("Reset should clear sleeping and auto restart of sleeping thread 2") {
    val sleepTimer = new ManualSleepTimerImpl
    sleepTimer setShouldSleep true
    sleepTimer setAutoActivate true
    val startTime = System.currentTimeMillis
    new Thread(new Runnable {
      override def run() = {
        Thread sleep 100L
        sleepTimer reset
      }
    }).start
    sleepTimer.sleep
    val time = System.currentTimeMillis - startTime
    time should be >= 100L
    time should be <= 120L
    sleepTimer.isAutoActivate shouldBe false
    sleepTimer.isShouldSleep shouldBe false
  }

}
