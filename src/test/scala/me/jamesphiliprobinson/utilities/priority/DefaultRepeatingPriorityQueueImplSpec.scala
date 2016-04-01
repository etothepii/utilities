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

  test("Can add three entities with different priorities and with on filter get a list ten long") {
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

  test("Can add four entities with different with some clashing increment") {
    val queue = new DefaultRepeatingPriorityQueueImpl[String]
    queue add ("a", 5)
    queue add ("b", 3)
    queue add ("d", 3)
    queue add ("c", 2)
    queue.size shouldBe 4
    val list = queue.next(13, _ => true).toArray
    list.size shouldBe 13
    List(list(0), list(1), list(2), list(3)) should contain allOf ("a", "b", "c", "d")
    list(4) shouldBe "c"
    List(list(5), list(6)) should contain allOf ("b", "d")
    list(7) shouldBe "c"
    list(8) shouldBe "a"
    List(list(9), list(10), list(11)) should contain allOf ("b", "c", "d")
    list(12) shouldBe "c"
  }

  test("Can add four entities with different sometimes clashing increments with filter off and then requesting more elements than can be returned") {
    val queue = new DefaultRepeatingPriorityQueueImpl[String]
    queue add ("a", 5)
    queue add ("b", 3)
    queue add ("d", 3)
    queue add ("c", 2)
    queue.size shouldBe 4
    val list = queue next 10
    list.size shouldBe 4
    list should contain allOf ("a", "b", "c", "d")
    queue.size shouldBe 0
  }

  test("Can add four entities with different sometimes clashing increments with changing filter") {
    val queue = new DefaultRepeatingPriorityQueueImpl[String]
    queue add ("a", 5)
    queue add ("b", 3)
    queue add ("d", 3)
    queue add ("c", 2)
    queue.size shouldBe 4
    val list = queue next(10, _ == "b")
    list.size shouldBe 10
    list.slice(0, 4) should contain allOf ("a", "b", "c", "d")
    list.slice(5, 10) should contain only ("b")
    queue.size shouldBe 1
  }

  test("Can add four entities with different sometimes clashing increments with filter off and then requesting more elements than can be returned using addAll") {
    val queue = new DefaultRepeatingPriorityQueueImpl[String]
    queue add ("a", 5)
    queue addAll ("b" :: "d" :: Nil, 3)
    queue add ("c", 2)
    queue.size shouldBe 4
    val list = queue next 10
    list.size shouldBe 4
    list should contain allOf ("a", "b", "c", "d")
    queue.size shouldBe 0
  }

  test("Can add four entities with different with some clashing increment using addAll") {
    val queue = new DefaultRepeatingPriorityQueueImpl[String]
    queue add ("a", 5)
    queue addAll ("b" :: "d" :: Nil, 3)
    queue add ("c", 2)
    queue.size shouldBe 4
    val list = queue.next(13, _ => true).toArray
    list.size shouldBe 13
    List(list(0), list(1), list(2), list(3)) should contain allOf ("a", "b", "c", "d")
    list(4) shouldBe "c"
    List(list(5), list(6)) should contain allOf ("b", "d")
    list(7) shouldBe "c"
    list(8) shouldBe "a"
    List(list(9), list(10), list(11)) should contain allOf ("b", "c", "d")
    list(12) shouldBe "c"
  }

  test("Once items have been taken but filtered back in new items should have the priority of old items") {
    val queue = new DefaultRepeatingPriorityQueueImpl[String]
    queue add ("a", 2)
    queue next (10, _ => true)
    queue add ("b", 3)
    queue.next(_ => true) shouldBe "b"
    queue.next(_ => true) shouldBe "a"
    queue.next(_ => true) shouldBe "b"
    queue.next(_ => true) shouldBe "a"
  }

  test("If an item is added for a second time the priority should just change") {
    val queue = new DefaultRepeatingPriorityQueueImpl[String]
    queue add ("a", 3)
    queue add ("a", 2)
    queue add ("b", 2)
    queue next(2, _ => true) should contain allOf ("a", "b")
    queue next(2, _ => true) should contain allOf ("a", "b")
    queue next(2, _ => true) should contain allOf ("a", "b")
    queue next(2, _ => true) should contain allOf ("a", "b")
    queue next(2, _ => true) should contain allOf ("a", "b")
    queue next(2, _ => true) should contain allOf ("a", "b")
  }

}
