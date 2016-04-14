package me.jamesphiliprobinson.utilities.priority

/**
  * Created by James Robinson on 27/03/2016.
  */
case class RepeatingPriorityQueueItem[T](val item: T, val increment: Int, val score: Long, var active: Boolean = true) extends Ordered[RepeatingPriorityQueueItem[T]] {

  val creationIndex = RepeatingPriorityQueueItem.getCreationIndex

  override def compare(that: RepeatingPriorityQueueItem[T]): Int = {
    if (this.score > that.score) {
      -1
    }
    else if (this.score < that.score) {
      1
    }
    else if (this.creationIndex > that.creationIndex) {
      -1
    }
    else if (this.creationIndex < that.creationIndex) {
      1
    }
    else {
      0
    }
  }

  def nextScore = score + increment

  def previousScore = score - increment
}

object RepeatingPriorityQueueItem {
  private var creationIndex = 0

  def getCreationIndex = RepeatingPriorityQueueItem.synchronized {
    creationIndex += 1
    creationIndex
  }
}
