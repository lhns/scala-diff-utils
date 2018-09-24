package com.github.difflib.patch

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
    patchable.insert(
      target,
      original.position, revised.lines
    )
  }

  override def restore[F](target: F)(implicit patchable: Patchable[F, T]): F =
    patchable.remove(
      target,
      revised.position, revised.size
    )

  override def toString: String =
    s"[InsertDelta, position: ${original.position}, lines: ${revised.lines.mkString("[", ", ", "]")}]"
}
