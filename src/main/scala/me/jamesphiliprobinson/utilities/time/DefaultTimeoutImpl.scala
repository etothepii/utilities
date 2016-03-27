package me.jamesphiliprobinson.utilities.time

/**
  * Created by James Robinson on 19/03/2016.
  */
class DefaultTimeoutImpl(val timeoutInMilliseconds: Long) extends Timeout {

  val startTime = System.currentTimeMillis

  def timedOutAt(currentTime: Long) = currentTime >= startTime + timeoutInMilliseconds

  override def timedOut: Boolean = {
    timedOutAt(System.currentTimeMillis)
  }

}
