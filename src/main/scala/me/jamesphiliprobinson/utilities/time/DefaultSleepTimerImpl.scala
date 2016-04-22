package me.jamesphiliprobinson.utilities.time

/**
  * Created by James Robinson on 03/04/2016.
  */
class DefaultSleepTimerImpl(var sleepLength: Long, var minimumSleepLength: Long) extends SleepTimer {

  var time = System.currentTimeMillis
  private val sync = new Object

  override def sleep = {
    try {
      sleepWithInterruptedException
    }
    catch {
      case ie: InterruptedException => reset
    }
  }

  override def reset = sync.synchronized {
    time = System.currentTimeMillis
  }

  override def sleepWithInterruptedException = {
    var sleepFor: Long = minimumSleepLength
    while (sleepFor > 0) {
      Thread sleep sleepFor
      sync.synchronized {
        sleepFor = time + sleepLength - System.currentTimeMillis
      }
    }
    reset
  }
}
