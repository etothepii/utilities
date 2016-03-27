package me.jamesphiliprobinson.utilities.priority

/**
  * Created by James Robinson on 27/03/2016.
  */
class DefaultPriorityQueueImpl[T] extends PriorityQueue[T] {

  override def add(t: T, priority: Int) = {
    throw new NotImplementedError
  }

  override def next(): T = {
    throw new NotImplementedError
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

}
