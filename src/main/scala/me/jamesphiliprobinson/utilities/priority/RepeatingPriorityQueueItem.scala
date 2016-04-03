package me.jamesphiliprobinson.utilities.priority

/**
  * Created by James Robinson on 27/03/2016.
  */
case class RepeatingPriorityQueueItem[T](val item: T, val increment: Int, val score: Long, var active: Boolean = true) extends Ordered[RepeatingPriorityQueueItem[T]] {
  override def compare(that: RepeatingPriorityQueueItem[T]): Int = {
    if (this.score > that.score) {
      -1
    }
    else if (this.score < that.score) {
      1
    }
    else {
      0
    }
  }

  def nextScore = score + increment

  def previousScore = score - increment
}
