package me.jamesphiliprobinson.utilities.priority

import scala.collection.mutable
import scala.collection.mutable.ListBuffer

/**
  * Created by James Robinson on 27/03/2016.
  */
class DefaultRepeatingPriorityQueueImpl[T] extends RepeatingPriorityQueue[T] {

  val queue = new mutable.PriorityQueue[RepeatingPriorityQueueItem[T]]
  val map = new mutable.HashMap[T, RepeatingPriorityQueueItem[T]]

  override def add(t: T, priority: Int) = add(t, priority, headScore)

  private def headScore = {
    val head = queue.headOption
    if (head.isDefined) {
      head.get.score
    }
    else {
      0L
    }
  }

  private def addNext(queueItem: RepeatingPriorityQueueItem[T]) = add(queueItem.item, queueItem.increment, queueItem.nextScore)

  def add(item: T, increment: Int, score: Long) = {
    val oldItem = map remove item
    val newScore = if (oldItem.isDefined) {
      oldItem.get.active = false
      Math.max(score, oldItem.get.previousScore + increment)
    }
    else {
      score
    }
    val rpqi = new RepeatingPriorityQueueItem[T](item, increment, newScore)
    map += ((item, rpqi))
    queue enqueue rpqi
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

  private def internalNext = {
    var queueItem: RepeatingPriorityQueueItem[T] = null
    while ((queueItem == null || !queueItem.active) && !queue.isEmpty) {
      queueItem = queue.dequeue
    }
    if (queueItem != null && queueItem.active) {
      map remove queueItem.item
      queueItem
    }
    else {
      null
    }
  }

  private def internalNext(leave: (T) => Boolean): RepeatingPriorityQueueItem[T] = {
    val queueItem = internalNext
    if (queueItem != null && leave(queueItem.item)) {
      addNext(queueItem)
    }
    queueItem
  }

  def next(leave: T => Boolean): T = {
    val queueItem = internalNext(leave)
    if (queueItem == null) {
      throw new EmptyRepeatingPriorityQueueException
    }
    queueItem.item
  }

  override def next(items: Int, leave: (T) => Boolean): Seq[T] = {
    val listBuffer = ListBuffer.empty[T]
    while (listBuffer.size < items && !queue.isEmpty) {
      val queueItem = internalNext(leave)
      if (queueItem != null) {
        listBuffer += queueItem.item
      }
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

  override def uniqueNext(items: Int): Seq[T] = uniqueNext(items, _ => false)

  override def uniqueNext(items: Int, leave: (T) => Boolean): Seq[T] = {
    val listBuffer = ListBuffer.empty[RepeatingPriorityQueueItem[T]]
    while (listBuffer.size < items && !queue.isEmpty) {
      listBuffer += internalNext
    }
    for (queueItem <- listBuffer) {
      if (leave(queueItem.item)) {
        add(queueItem.item, queueItem.increment, queueItem.nextScore)
      }
    }
    listBuffer.map(_.item)
  }
}
