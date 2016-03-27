package me.jamesphiliprobinson.utilities.time

import org.scalatest.{Matchers, FunSuite}

/**
  * Created by James Robinson on 19/03/2016.
  */
class DefaultTimeoutImplSpec extends FunSuite with Matchers {

  test("Can timeout after 20 minutes") {
    val timeout = new DefaultTimeoutImpl(20 * 60 * 1000L);
    timeout.timedOutAt(timeout.startTime) should be(false)
    timeout.timedOutAt(timeout.startTime + 1199999) should be(false)
    timeout.timedOutAt(timeout.startTime + 1200000) should be(true)
    timeout.timedOutAt(timeout.startTime + 1200001) should be(true)
  }

}
