package me.jamesphiliprobinson.utilities.priority

import org.scalatest.{FunSuite, Matchers}

/**
  * Created by James Robinson on 28/03/2016.
  */
class RepeatingPriorityQueueItemSpec extends FunSuite with Matchers {

  test("Can correctly compare items greater than") {
    val a = new RepeatingPriorityQueueItem[String]("a", 1, 1)
    val b = new RepeatingPriorityQueueItem[String]("b", 7, 5)
    a compare b should be > 0
  }

  test("Can correctly compare items less than") {
    val a = new RepeatingPriorityQueueItem[String]("a", 1, 5)
    val b = new RepeatingPriorityQueueItem[String]("b", 7, 1)
    a compare b should be < 0
  }

  test("Can correctly compare items equal based on order created") {
    val a = new RepeatingPriorityQueueItem[String]("a", 1, 5)
    val b = new RepeatingPriorityQueueItem[String]("b", 7, 5)
    a compare b shouldBe 1
    b compare a shouldBe -1
  }

  test("Can correctly increment to nextScore") {
    val a = new RepeatingPriorityQueueItem[String]("a", 7, 5)
    a.nextScore shouldBe 12
  }

  test("Can correctly decrement to nextScore") {
    val a = new RepeatingPriorityQueueItem[String]("a", 7, 5)
    a.previousScore shouldBe -2
  }

}
