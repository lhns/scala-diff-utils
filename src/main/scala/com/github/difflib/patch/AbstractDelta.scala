package com.github.difflib.patch

/**
  * Abstract delta between a source and a target.
  *
  * @author Tobias Warneke (t.warneke@gmx.net)
  */
abstract case class AbstractDelta[T](`type`: DeltaType,
                                     original: Chunk[T],
                                     revised: Chunk[T]) {
  /**
    * Verify the chunk of this delta, to fit the target.
    *
    * @param target
    * @throws PatchFailedException
    */
  @throws[PatchFailedException]
  protected def verifyChunk(target: Seq[T]): Unit =
    original.verify(target)

  @throws[PatchFailedException]
  def applyTo(target: Seq[T]): Seq[T]

  def restore(target: Seq[T]): Seq[T]
}
