package me.jamesphiliprobinson.utilities.priority

import scala.collection.mutable

/**
  * Created by James Robinson on 27/03/2016.
  */
class DefaultRepeatingPriorityQueueImpl[T] extends RepeatingPriorityQueue[T] {

  val queue = new mutable.PriorityQueue[RepeatingPriorityQueueItem[T]]

  override def add(t: T, priority: Int) = add(t, priority, 0)

  def add(t: T, priority: Int, score: Int) = {
    queue enqueue(new RepeatingPriorityQueueItem[T](t, priority, score))
  }

  override def next(): T = {
    queue.dequeue.item
  }

  override def next(items: Int): Seq[T] = {
    throw new NotImplementedError
  }

  override def removeAll(seq: Seq[T]) = {
    throw new NotImplementedError
  }

  override def remove(t: T) = {
    throw new NotImplementedError
  }

  override def addAll(seq: Seq[T], priority: Int) = {
    throw new NotImplementedError
  }

  override def next(leave: (T) => Boolean): T = {
    val queueItem = queue.dequeue
    if (leave(queueItem.item)) {
      queue.enqueue(queueItem.next)
    }
    queueItem.item
  }

  override def next(items: Int, leave: (T) => Boolean): Seq[T] = {
    throw new NotImplementedError
  }

  override def size(): Int = {
    queue.size
  }
}
