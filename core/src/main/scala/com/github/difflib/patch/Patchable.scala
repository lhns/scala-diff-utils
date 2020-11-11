package com.github.difflib.patch

import com.github.difflib.DiffUtils
import com.github.difflib.algorithm.{DiffAlgorithm, DiffAlgorithmListener}

trait Patchable[F, T] {
  def remove(self: F, position: Int, size: Int): F
  def insert(self: F, position: Int, elements: Seq[T]): F
  def toSeq(self: F): Seq[T]
}

object Patchable {

  implicit class PatchableOps[F, T](val self: F)(implicit patchable: Patchable[F, T]) {
    def remove(position: Int, size: Int): F = patchable.remove(self, position, size)
    def insert(position: Int, elements: Seq[T]): F = patchable.insert(self, position, elements)
    def toSeq: Seq[T] = patchable.toSeq(self)

    @throws[PatchFailedException]
    def patch(revised: Seq[T],
              algorithm: DiffAlgorithm[T] = DiffUtils.defaultDiffAlgorithm,
              progress: DiffAlgorithmListener = DiffAlgorithmListener.Empty): F =
      DiffUtils.patch(self, DiffUtils.diff(self.toSeq, revised, algorithm, progress))
  }

  implicit def SeqPatchable[T]: Patchable[Seq[T], T] = new Patchable[Seq[T], T] {
    override def remove(self: Seq[T], position: Int, size: Int): Seq[T] =
      self.take(position) ++ self.drop(position + size)

    override def insert(self: Seq[T], position: Int, elements: Seq[T]): Seq[T] =
      self.take(position) ++ elements ++ self.drop(position)

    override def toSeq(self: Seq[T]): Seq[T] = self
  }
}
