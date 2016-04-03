package me.jamesphiliprobinson.utilities.time

/**
  * Created by James Robinson on 03/04/2016.
  */
class DefaultSleepTimerImpl(var sleepLength: Long, var minimumSleepLength: Long) extends SleepTimer {

  var time = System.currentTimeMillis

  override def setSleepLength(sleepLength: Long) = {
    this.sleepLength = sleepLength
  }

  override def setMinimumSleepLength(minimumSleepLength: Long) = {
    this.minimumSleepLength = minimumSleepLength
  }

  override def sleep = {
    val sleepFor = time + sleepLength - System.currentTimeMillis
    Thread sleep Math.max(minimumSleepLength, sleepFor)
    reset
  }

  override def reset = {
    time = System.currentTimeMillis
  }

}
