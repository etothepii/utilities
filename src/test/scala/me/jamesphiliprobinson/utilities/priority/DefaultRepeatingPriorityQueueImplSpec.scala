package me.jamesphiliprobinson.utilities.priority

import org.scalatest.{FunSuite, Matchers}

/**
  * Created by James Robinson on 27/03/2016.
  */
class DefaultRepeatingPriorityQueueImplSpec extends FunSuite with Matchers {

  test("Can add single entity to queue and it is available") {
    val queue = new DefaultRepeatingPriorityQueueImpl[String]
    queue add ("a", 5)
    queue.size should be (1)
    val result = queue.next
    val expected = "a"
    result should be(expected)
    queue.size should be (0)
  }

  test("Can add two entities with different priorities and have the come out in the right order") {
    val queue = new DefaultRepeatingPriorityQueueImpl[String]
    queue add ("a", 5, 5)
    queue add ("b", 2, 2)
    queue.size should be (2)
    queue.next should be ("b")
    queue.next should be ("a")
    queue.size should be (0)
  }

  test("Can add two entities with different priorities and leaving them in have them come out in the right order") {
    val queue = new DefaultRepeatingPriorityQueueImpl[String]
    queue add ("a", 5, 5)
    queue add ("b", 2, 2)
    queue.size should be (2)
    queue.next(_ => true) should be ("b")
    queue.size should be (2)
    queue.next(_ => true) should be ("b")
    queue.size should be (2)
    queue.next(_ => true) should be ("a")
    queue.size should be (2)
  }

}
