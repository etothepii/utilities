package me.jamesphiliprobinson.utilities.priority

import scala.collection.mutable
import scala.collection.mutable.ListBuffer

/**
  * Created by James Robinson on 27/03/2016.
  */
class DefaultRepeatingPriorityQueueImpl[T] extends RepeatingPriorityQueue[T] {

  val queue = new mutable.PriorityQueue[RepeatingPriorityQueueItem[T]]
  val map = new mutable.HashMap[T, RepeatingPriorityQueueItem[T]]
  var maxScore = 0L

  override def add(t: T, priority: Int) = add(t, priority, maxScore)

  def add(item: T, increment: Int, score: Long) = {
    val oldItem = map remove item
    val newScore = if (oldItem.isDefined) {
      oldItem.get.active = false
      Math.max(maxScore, oldItem.get.previousScore + increment)
    }
    else {
      score
    }
    val rpqi = new RepeatingPriorityQueueItem[T](item, increment, newScore)
    map += ((item, rpqi))
    queue enqueue(rpqi)
  }

  override def next(): T = {
    next(_ => false)
  }

  override def next(items: Int): Seq[T] = {
    next(items, _ => false)
  }

  override def addAll(seq: Seq[T], priority: Int) = {
    for (t <- seq) {
      add(t, priority)
    }
  }

  override def next(leave: (T) => Boolean): T = {
    var queueItem = queue.dequeue
    while (!queueItem.active) {
      queueItem = queue.dequeue
    }
    maxScore = queueItem.score
    map remove queueItem.item
    if (leave(queueItem.item)) {
      add(queueItem.item, queueItem.increment, queueItem.nextScore)
    }
    queueItem.item
  }

  override def next(items: Int, leave: (T) => Boolean): Seq[T] = {
    var index = 0
    val listBuffer = ListBuffer.empty[T]
    while (index < items && !queue.isEmpty) {
      index = index + 1
      listBuffer += next(leave)
    }
    listBuffer
  }

  override def size(): Int = {
    queue.size
  }

  override def remove(t: T) = {
    val item = map remove t
    if (item.isDefined) {
      item.get.active = false
    }
  }
}
