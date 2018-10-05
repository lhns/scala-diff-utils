package com.github.difflib.patch

import com.github.difflib.patch.Patchable._

/**
  * Describes the add-delta between original and revised texts.
  *
  * @author <a href="dm.naumenko@gmail.com">Dmitry Naumenko</a>
  * @param T The type of the compared elements in the 'lines'.
  */
class InsertDelta[T](original: Chunk[T],
                     revised: Chunk[T]) extends AbstractDelta[T](DeltaType.Delete, original, revised) {
  @throws[PatchFailedException]
  override def applyTo[F](target: F)(implicit patchable: Patchable[F, T]): F = {
    verifyChunk(patchable.toSeq(target))
    target.insert(original.position, revised.lines)
  }

  override def restore[F](target: F)(implicit patchable: Patchable[F, T]): F =
    target.remove(revised.position, revised.size)

  override def toString: String =
    s"[InsertDelta, position: ${original.position}, lines: ${revised.lines.mkString("[", ", ", "]")}]"
}
