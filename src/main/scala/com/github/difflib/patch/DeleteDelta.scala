package com.github.difflib.patch

/**
  * Describes the delete-delta between original and revised texts.
  *
  * @author <a href="dm.naumenko@gmail.com">Dmitry Naumenko</a>
  * @param T The type of the compared elements in the 'lines'.
  */
class DeleteDelta[T](original: Chunk[T],
                     revised: Chunk[T]) extends AbstractDelta[T](DeltaType.DELETE, original, revised) {
  @throws[PatchFailedException]
  def applyTo(target: List[T]): List[T] = {
    verifyChunk(target)
    target.take(original.position) ++
      target.drop(original.position + original.size)
  }

  def restore(target: List[T]): List[T] =
    target.take(revised.position) ++
      original.lines ++
      target.drop(revised.position)

  override def toString =
    s"[DeleteDelta, position: ${original.position}, lines: ${original.lines.mkString("[", ", ", "]")}]"
}
