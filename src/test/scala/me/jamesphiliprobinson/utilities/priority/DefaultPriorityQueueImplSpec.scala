package me.jamesphiliprobinson.utilities.priority

import org.scalatest.{FunSuite, Matchers}

/**
  * Created by James Robinson on 27/03/2016.
  */
class DefaultPriorityQueueImplSpec extends FunSuite with Matchers {

  test("Can add single entity to queue and it is available") {
    val queue = new DefaultPriorityQueueImpl[String]
    queue add ("a", 5)
    val result = queue.next
    val expected = "a"
    result should be(expected)
  }

}
