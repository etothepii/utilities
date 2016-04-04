package me.jamesphiliprobinson.utilities.time

/**
  * Created by James Robinson on 03/04/2016.
  */
trait SleepTimer {

  def sleep
  def sleepWithInterruptedException
  def reset

}
