package me.jamesphiliprobinson.utilities.time

/**
  * Created by James Robinson on 03/04/2016.
  */
trait SleepTimer {

  def setSleepLength(sleepLength: Long)
  def setMinimumSleepLength(minimumSleepLength: Long)
  def sleep
  def reset

}
