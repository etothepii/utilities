package me.jamesphiliprobinson.utilities.implicits

import me.jamesphiliprobinson.utilities.implicits.ThreadingConversions._
import org.scalatest.{BeforeAndAfter, FunSuite, Matchers}

/**
  * Created by James Robinson on 22/04/2016.
  */
class ThreadingConversionsSpec extends FunSuite with Matchers with BeforeAndAfter {

  val sync = new Object

  var salutation: String = _

  before {
    salutation = ""
  }

  def setSalutation = sync.synchronized {
    salutation = "hello"
    sync.notify
  }

  def getSalutation: String = sync.synchronized {
    salutation = "hello again"
    sync.notify
    salutation
  }

  def setSalutation(name: String) = sync.synchronized {
    salutation = s"hello $name"
    sync.notify
  }

  test("can set value with converted runnable") {
    sync.synchronized {
      new Thread(setSalutation).start
      Thread sleep 100L
      sync.wait
    }
    salutation shouldBe "hello"
  }

  test("can set value with value and converted runnable") {
    sync.synchronized {
      new Thread(setSalutation("fred")).start
      Thread sleep 100L
      sync.wait
    }
    salutation shouldBe "hello fred"
  }

  test("can get value with converted runnable") {
    sync.synchronized {
      new Thread(getSalutation, "").start
      Thread sleep 100L
      sync.wait
    }
    Thread sleep 100
    salutation shouldBe "hello again"
  }


}
