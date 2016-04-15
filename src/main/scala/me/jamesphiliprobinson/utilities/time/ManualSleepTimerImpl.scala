package me.jamesphiliprobinson.utilities.time

/**
  * Created by James Robinson on 04/04/2016.
  */
class ManualSleepTimerImpl extends SleepTimer {
  private val sync = new Object
  private var shouldSleep = false
  private var autoActivate = false

  override def sleep = {
    if (isShouldSleep) {
      sync.synchronized {
        if (isShouldSleep) {
          sync.wait
          if (isAutoActivate) {
            setShouldSleep(true)
          }
        }
      }
    }
    if (isAutoActivate) {
      sync.synchronized {
        if (isAutoActivate) {
          setShouldSleep(true)
        }
      }
    }
  }
  override def sleepWithInterruptedException = {}
  override def reset = {}

  def setShouldSleep(shouldSleep: Boolean) = sync.synchronized {
    this.shouldSleep = shouldSleep
    if (!shouldSleep) {
      sync.notify
    }
  }

  def isShouldSleep = sync.synchronized {
    shouldSleep
  }

  def isAutoActivate = sync.synchronized {
    autoActivate
  }

  def setAutoActivate(autoActivate: Boolean) = sync.synchronized {
    this.autoActivate = autoActivate
  }
}
