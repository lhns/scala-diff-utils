package com.github.difflib.patch

/**
  * Describes the change-delta between original and revised texts.
  *
  * @author <a href="dm.naumenko@gmail.com">Dmitry Naumenko</a>
  * @param T The type of the compared elements in the data 'lines'.
  */
class ChangeDelta[T](original: Chunk[T],
                     revised: Chunk[T]) extends AbstractDelta[T](DeltaType.CHANGE, original, revised) {
  @throws[PatchFailedException]
  def applyTo(target: List[T]): List[T] = {
    verifyChunk(target)
    target.take(original.position) ++
      revised.lines ++
      target.drop(original.position + original.size)
  }

  def restore(target: List[T]): List[T] =
    target.take(revised.position) ++
      original.lines ++
      target.drop(revised.position + revised.size)

  override def toString: String =
    s"[ChangeDelta, position: ${original.position}, lines: ${original.lines.mkString("[", ", ", "]")} to ${revised.lines.mkString("[", ", ", "]")}]"
}
