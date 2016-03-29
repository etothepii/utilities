package me.jamesphiliprobinson.utilities.priority

import scala.collection.mutable
import scala.collection.mutable.ListBuffer

/**
  * Created by James Robinson on 27/03/2016.
  */
class DefaultRepeatingPriorityQueueImpl[T] extends RepeatingPriorityQueue[T] {

  val queue = new mutable.PriorityQueue[RepeatingPriorityQueueItem[T]]
  var maxScore = 0L

  override def add(t: T, priority: Int) = add(t, priority, maxScore)

  def add(t: T, priority: Int, score: Long) = {
    queue enqueue(new RepeatingPriorityQueueItem[T](t, priority, score))
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
    val queueItem = queue.dequeue
    maxScore = queueItem.score
    if (leave(queueItem.item)) {
      queue.enqueue(queueItem.next)
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
}
