package me.jamesphiliprobinson.utilities.priority

/**
  * Created by James Robinson on 27/03/2016.
  */
trait PriorityQueue[T] {

  def add(t: T, priority: Int)
  def addAll(seq: Seq[T], priority: Int)
  def next() : T
  def next(items: Int) : Seq[T]
  def next(leave: T => Boolean) : T
  def next(items: Int, leave: T => Boolean) : Seq[T]
  def remove(t: T)
  def removeAll(seq: Seq[T])
  def size(): Int

}
