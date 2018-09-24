package com.github.difflib.patch

import com.github.difflib.algorithm.Change

case class Patch[T] private(deltas: List[AbstractDelta[T]]) {
  @throws[PatchFailedException]
  def applyTo(target: List[T]): List[T] =
    deltas.reverse.foldLeft(target) { (acc, delta) =>
      delta.applyTo(acc)
    }

  def restore(target: List[T]): List[T] =
    deltas.reverse.foldLeft(target) { (acc, delta) =>
      delta.restore(acc)
    }

  override def toString: String = s"Patch{deltas=${deltas.mkString("[", ", ", "]")}}"
}

object Patch {
  def apply[T](deltas: List[AbstractDelta[T]]): Patch[T] =
    new Patch[T](deltas.sortBy(_.original.position))

  def apply[T](deltas: Seq[AbstractDelta[T]]): Patch[T] =
    Patch[T](deltas.toList)

  def generate[T](original: List[T], revised: List[T], changes: List[Change]): Patch[T] = Patch[T](
    for (change <- changes) yield {
      val orgChunk = Chunk[T](change.startOriginal, original.slice(change.startOriginal, change.endOriginal))
      val revChunk = Chunk[T](change.startRevised, revised.slice(change.startRevised, change.endRevised))
      change.deltaType match {
        case DeltaType.Delete => new DeleteDelta[T](orgChunk, revChunk)
        case DeltaType.Insert => new InsertDelta[T](orgChunk, revChunk)
        case DeltaType.Change => new ChangeDelta[T](orgChunk, revChunk)
      }
    }
  )
}
