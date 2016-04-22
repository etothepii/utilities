package me.jamesphiliprobinson.utilities.implicits

/**
  * Created by James Robinson on 22/04/2016.
  */
object ThreadingConversions {
  implicit def funcToRunnable( func : => Any ) = new Runnable { def run = func }
}
