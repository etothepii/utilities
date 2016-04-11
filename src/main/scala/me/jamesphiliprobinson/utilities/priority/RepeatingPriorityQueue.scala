package me.jamesphiliprobinson.utilities.priority

/**
  * Created by James Robinson on 27/03/2016.
  */
trait RepeatingPriorityQueue[T] {

  def add(t: T, priority: Int)
  def addAll(seq: Seq[T], priority: Int)
  def next() : T
  def next(items: Int) : Seq[T]
  def next(leave: T => Boolean) : T
  def next(items: Int, leave: T => Boolean) : Seq[T]
  def size(): Int
  def remove(t: T)

}
