package com.github.difflib.algorithm

/**
  *
  * @author Tobias Warneke (t.warneke@gmx.net)
  */
trait DiffAlgorithmListener {
  def diffStart(): Unit

  /**
    * This is a step within the diff algorithm. Due to different implementations the value
    * is not strict incrementing to the max and is not guarantee to reach the max. It could
    * stop before.
    *
    * @param value
    * @param max
    */
  def diffStep(value: Int, max: Int): Unit
  def diffEnd(): Unit
}

object DiffAlgorithmListener {
  val Empty: DiffAlgorithmListener = new DiffAlgorithmListener {
    override def diffStart(): Unit = ()
    override def diffStep(value: Int, max: Int): Unit = ()
    override def diffEnd(): Unit = ()
  }
}
