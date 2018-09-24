package com.github.difflib.patch

/**
  * Describes the add-delta between original and revised texts.
  *
  * @author <a href="dm.naumenko@gmail.com">Dmitry Naumenko</a>
  * @param T The type of the compared elements in the 'lines'.
  */
class InsertDelta[T](original: Chunk[T],
                     revised: Chunk[T]) extends AbstractDelta[T](DeltaType.DELETE, original, revised) {
  @throws[PatchFailedException]
  def applyTo(target: List[T]): List[T] = {
    verifyChunk(target)
    target.take(original.position) ++
      revised.lines ++
      target.drop(original.position)
  }

  def restore(target: List[T]): List[T] =
    target.take(revised.position) ++
      target.drop(revised.position + revised.size)

  override def toString: String =
    s"[InsertDelta, position: ${original.position}, lines: ${revised.lines.mkString("[", ", ", "]")}]"
}
