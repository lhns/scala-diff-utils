package com.github.difflib.patch

/**
  * Describes the change-delta between original and revised texts.
  *
  * @author <a href="dm.naumenko@gmail.com">Dmitry Naumenko</a>
  * @param T The type of the compared elements in the data 'lines'.
  */
class ChangeDelta[T](original: Chunk[T],
                     revised: Chunk[T]) extends AbstractDelta[T](DeltaType.Change, original, revised) {
  @throws[PatchFailedException]
  override def applyTo[F](target: F)(implicit patchable: Patchable[F, T]): F = {
    verifyChunk(patchable.toSeq(target))
    patchable.insert(
      patchable.remove(
        target,
        original.position, original.size
      ),
      original.position, revised.lines
    )
  }

  override def restore[F](target: F)(implicit patchable: Patchable[F, T]): F =
    patchable.insert(
      patchable.remove(
        target,
        revised.position, revised.size
      ),
      revised.position, original.lines
    )

  override def toString: String =
    s"[ChangeDelta, position: ${original.position}, lines: ${original.lines.mkString("[", ", ", "]")} to ${revised.lines.mkString("[", ", ", "]")}]"
}
