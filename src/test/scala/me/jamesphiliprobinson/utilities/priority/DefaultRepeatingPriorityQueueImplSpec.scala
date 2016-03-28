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
    queue.size shouldBe 2
    queue.next shouldBe "b"
    queue.next shouldBe "a"
    queue.size shouldBe 0
  }

  test("Can add two entities with different priorities and leaving them in have them come out in the right order") {
    val queue = new DefaultRepeatingPriorityQueueImpl[String]
    queue add ("a", 5, 5)
    queue add ("b", 2, 2)
    queue.size shouldBe 2
    queue.next(_ => true) shouldBe "b"
    queue.size shouldBe 2
    queue.next(_ => true) shouldBe "b"
    queue.size shouldBe 2
    queue.next(_ => true) shouldBe "a"
    queue.size shouldBe 2
  }

  test("can add three entities with different priorities and with on filter get a list ten long") {
    val queue = new DefaultRepeatingPriorityQueueImpl[String]
    queue add ("a", 5)
    queue add ("b", 3)
    queue add ("c", 2)
    queue.size shouldBe 3
    val list = queue.next(10, _ => true).toArray
    list.size shouldBe 10
    List(list(0), list(1), list(2)) should contain allOf ("a", "b", "c")
    list(3) shouldBe "c"
    list(4) shouldBe "b"
    list(5) shouldBe "c"
    list(6) shouldBe "a"
    List(list(7), list(8)) should contain allOf ("b", "c")
    list(9) shouldBe "c"
    queue.size shouldBe 3
  }

}
